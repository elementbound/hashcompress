package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;

/**
 * Class to decompress a single block of data.
 */
public class Dehash {
    private final RandomBlockSupplier blockSupplier;
    private final DigestUtils digestUtils;

    public Dehash(RandomBlockSupplier blockSupplier, DigestUtils digestUtils) {
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
        while (true) {
            byte[] block = ArrayUtils.toPrimitive(blockSupplier.get());

            if (Arrays.equals(digestUtils.digest(block), data)) {
                return block;
            }
        }
    }
}
