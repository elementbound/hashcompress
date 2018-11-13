package com.github.elementbound.hashcompress.enhash.impl;

import com.github.elementbound.hashcompress.enhash.hash.Dehash;
import com.github.elementbound.hashcompress.enhash.hash.Enhash;
import com.github.elementbound.hashcompress.enhash.reporting.HashCompressorProgress;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ThreadedHashCompressor extends AbstractHashCompressor {
    public static final int N_THREADS = 12;
    private final int compressedBlockSize;
    private final int decompressedBlockSize;
    private final Enhash enhash;
    private final Dehash dehash;

    public ThreadedHashCompressor(int compressedBlockSize, int decompressedBlockSize, Enhash enhash, Dehash dehash) {
        this.compressedBlockSize = compressedBlockSize;
        this.decompressedBlockSize = decompressedBlockSize;
        this.enhash = enhash;
        this.dehash = dehash;
    }

    @Override
    public void compress(InputStream in, OutputStream out) throws IOException {
        byte[] inputBuffer = new byte[decompressedBlockSize];
        int blocksDone = 0;

        while (IOUtils.read(in, inputBuffer) > 0) {
            out.write(enhash.consume(inputBuffer));
            broadcastProgress(new HashCompressorProgress(++blocksDone, false));
        }

        broadcastProgress(new HashCompressorProgress(++blocksDone, true));
    }

    @Override
    public void decompress(InputStream in, OutputStream out) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(N_THREADS);
        List<Future<byte[]>> dehashedBlocks = new ArrayList<>(N_THREADS);

        byte[] inputBuffer = new byte[compressedBlockSize];
        int blocksDone = 0;

        while (in.available() > 0) {
            for (int i = 0; i < N_THREADS && IOUtils.read(in, inputBuffer) > 0; ++i) {
                byte[] currentBlock = Arrays.copyOf(inputBuffer, compressedBlockSize);
                Future<byte[]> result = executorService.submit(() -> dehash.consume(currentBlock));
                dehashedBlocks.add(result);
            }

            dehashedBlocks.stream()
                    .map(this::throwingGet)
                    .forEachOrdered(throwingWrite(out));

            blocksDone += N_THREADS;
            broadcastProgress(new HashCompressorProgress(blocksDone, false));
        }

        executorService.shutdown();
        broadcastProgress(new HashCompressorProgress(blocksDone, true));
    }

    protected Consumer<byte[]> throwingWrite(OutputStream out) {
        return chunk -> {
            try {
                out.write(chunk);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private <T> T throwingGet(Future<T> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
