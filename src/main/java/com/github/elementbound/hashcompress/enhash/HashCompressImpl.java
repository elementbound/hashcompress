package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;

public class HashCompressImpl {
    private final int compressedBlockSize;
    private final int decompressedBlockSize;
    private final DigestUtils digestUtils;
    private final Enhash enhash;
    private final Dehash dehash;

    public HashCompressImpl(int compressedBlockSize, int decompressedBlockSize, DigestUtils digestUtils, Enhash enhash, Dehash dehash) {
        this.compressedBlockSize = compressedBlockSize;
        this.decompressedBlockSize = decompressedBlockSize;
        this.digestUtils = digestUtils;
        this.enhash = enhash;
        this.dehash = dehash;
    }

    public void compress(InputStream in, OutputStream out) throws IOException {
        byte[] inputBuffer = new byte[decompressedBlockSize];

        while (IOUtils.read(in, inputBuffer) > 0) {
            out.write(enhash.consume(inputBuffer));
        }
    }

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
