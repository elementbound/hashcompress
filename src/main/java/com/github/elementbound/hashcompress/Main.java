package com.github.elementbound.hashcompress;

import com.github.elementbound.hashcompress.enhash.HashCompressFactory;
import com.github.elementbound.hashcompress.enhash.HashCompressImpl;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            printUsage();
            return;
        }

        Mode mode = Mode.fromString(args[0]);
        String inputFile = args[1];
        String outputFile = args[2];

        InputStream inputStream = new FileInputStream(inputFile);
        OutputStream outputStream = new FileOutputStream(outputFile);

        HashCompressImpl hashCompress = HashCompressFactory.getHashCompress(3);

        switch (mode) {
            case COMPRESS:
                hashCompress.compress(inputStream, outputStream);
                break;

            case DECOMPRESS:
                hashCompress.decompress(inputStream, outputStream);
                break;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: hashcompress <compress|decompress> <input> <output>");
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
