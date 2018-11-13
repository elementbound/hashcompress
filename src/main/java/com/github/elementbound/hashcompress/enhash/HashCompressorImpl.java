package com.github.elementbound.hashcompress.enhash;

import com.github.elementbound.hashcompress.enhash.hash.Dehash;
import com.github.elementbound.hashcompress.enhash.hash.Enhash;
import com.github.elementbound.hashcompress.enhash.reporting.HashCompressorProgress;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Simple {@link HashCompressor} implementation.
 */
public class HashCompressorImpl implements ReportingHashCompressor {
    private final int compressedBlockSize;
    private final int decompressedBlockSize;
    private final Enhash enhash;
    private final Dehash dehash;
    private final List<Consumer<HashCompressorProgress>> subscribers = new ArrayList<>();

    public HashCompressorImpl(int compressedBlockSize, int decompressedBlockSize, Enhash enhash, Dehash dehash) {
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
        byte[] inputBuffer = new byte[compressedBlockSize];
        int blocksDone = 0;

        while (IOUtils.read(in, inputBuffer) > 0) {
            out.write(dehash.consume(inputBuffer));
            broadcastProgress(new HashCompressorProgress(++blocksDone, false));
        }

        broadcastProgress(new HashCompressorProgress(++blocksDone, true));
    }

    @Override
    public void onProgress(Consumer<HashCompressorProgress> consumer) {
        subscribers.add(consumer);
    }


    private Consumer<byte[]> throwingWrite(OutputStream out) {
        return chunk -> {
            try {
                out.write(chunk);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    private void broadcastProgress(HashCompressorProgress progress) {
        subscribers.forEach(consumer -> consumer.accept(progress));
    }
}
