package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Random;
import java.util.function.Supplier;

public class RandomBlockSupplier implements Supplier<Byte[]> {
    private final int blockSize;
    private final Random random;

    public RandomBlockSupplier(int blockSize, Random random) {
        this.blockSize = blockSize;
        this.random = random;
    }

    public Byte[] get() {
        byte[] result = new byte[blockSize];
        random.nextBytes(result);

        return ArrayUtils.toObject(result);
    }
}
