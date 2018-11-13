package com.github.elementbound.hashcompress.enhash.hash;

import com.github.elementbound.hashcompress.enhash.supplier.BlockSupplier;
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
    public synchronized byte[] consume(byte[] data) {
        while (true) {
            byte[] block = blockSupplier.getBytes();
            byte[] hashed = digestUtils.digest(block);

            if (Arrays.equals(hashed, data)) {
                return block;
            }
        }
    }
}
