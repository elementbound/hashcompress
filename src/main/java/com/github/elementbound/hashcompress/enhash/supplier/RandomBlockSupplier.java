package com.github.elementbound.hashcompress.enhash.supplier;

import java.util.Random;

/**
 * Class to provide byte blocks with random contents.
 */
public class RandomBlockSupplier implements BlockSupplier {
    private final int blockSize;
    private final Random random;

    public RandomBlockSupplier(int blockSize, Random random) {
        this.blockSize = blockSize;
        this.random = random;
    }

    @Override
    public byte[] getBytes() {
        byte[] result = new byte[blockSize];
        random.nextBytes(result);

        return result;
    }

    @Override
    public int getBlockSize() {
        return this.blockSize;
    }
}
