package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * Class to decompress a single block of data.
 */
public class Dehash {
    private final BlockSupplier blockSupplier;
    private final DigestUtils digestUtils;

    public Dehash(BlockSupplier blockSupplier, DigestUtils digestUtils) {
        this.blockSupplier = blockSupplier;
        this.digestUtils = digestUtils;
    }

    /**
     * Decompress a single block of data.
     *
     * @param data to decompress
     * @return decompressed data
     */
    public byte[] consume(byte[] data) {
        for (long i = 0; true; ++i) {
            byte[] block = blockSupplier.getBytes();
            byte[] hashed = digestUtils.digest(block);

            // if ((i % 16384) == 0) {
            //     System.out.printf("Dehashing attempt %d\r", i);
            // }

            if (Arrays.equals(hashed, data)) {
                System.out.printf("Dehashing attempt %d success\n", i);
                return block;
            }
        }
    }
}
