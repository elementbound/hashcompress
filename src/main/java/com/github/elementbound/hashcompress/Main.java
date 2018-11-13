package com.github.elementbound.hashcompress;

import com.github.elementbound.hashcompress.enhash.HashCompressor;
import com.github.elementbound.hashcompress.enhash.HashCompressorFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            printUsage();
            return;
        }

        Mode mode = Mode.fromString(args[0]);
        int blockSize = Integer.parseInt(args[1]);
        String inputFile = args[2];
        String outputFile = args[3];

        InputStream inputStream = new FileInputStream(inputFile);
        OutputStream outputStream = new FileOutputStream(outputFile);

        HashCompressor hashCompressor = HashCompressorFactory.getHashCompress(blockSize);

        switch (mode) {
            case COMPRESS:
                hashCompressor.compress(inputStream, outputStream);
                break;

            case DECOMPRESS:
                hashCompressor.decompress(inputStream, outputStream);
                break;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: hashcompress <compress|decompress> <blockSize> <input> <output>");
    }

    private enum Mode {
        COMPRESS,
        DECOMPRESS;

        static Mode fromString(String mode) {
            if (mode.toLowerCase().equals("compress")) {
                return COMPRESS;
            } else if (mode.toLowerCase().equals("decompress")) {
                return DECOMPRESS;
            } else {
                throw new RuntimeException(String.format("Unknown mode: %s", mode));
            }
        }
    }
}
