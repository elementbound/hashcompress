package com.github.elementbound.hashcompress.enhash;

import com.github.elementbound.hashcompress.enhash.hash.Dehash;
import com.github.elementbound.hashcompress.enhash.hash.Enhash;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

/**
 * Simple {@link HashCompressor} implementation.
 */
public class HashCompressorImpl implements HashCompressor {
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

        while (IOUtils.read(in, inputBuffer) > 0) {
            out.write(enhash.consume(inputBuffer));
        }
    }

    @Override
    public void decompress(InputStream in, OutputStream out) throws IOException {
        byte[] inputBuffer = new byte[compressedBlockSize];

        while (IOUtils.read(in, inputBuffer) > 0) {
            out.write(dehash.consume(inputBuffer));
        }
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
}
