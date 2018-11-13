package com.github.elementbound.hashcompress.enhash.impl;

import com.github.elementbound.hashcompress.enhash.HashCompressor;
import com.github.elementbound.hashcompress.enhash.hash.Dehash;
import com.github.elementbound.hashcompress.enhash.hash.Enhash;
import com.github.elementbound.hashcompress.enhash.reporting.HashCompressorProgress;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple {@link HashCompressor} implementation.
 */
public class HashCompressorImpl extends AbstractHashCompressor {
    private final int compressedBlockSize;
    private final int decompressedBlockSize;
    private final Enhash enhash;
    private final Dehash dehash;

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


}
