package com.github.elementbound.hashcompress.enhash.supplier;

import java.util.Arrays;

/**
 * <p>Class to provide blocks in an iterative manner.
 * <p>This class will treat each block as a huge binary number, and increment
 * it on each call.
 */
public class IterativeBlockSupplier implements BlockSupplier {
    private final int blockSize;
    private final byte[] block;

    public IterativeBlockSupplier(int blockSize) {
        this.blockSize = blockSize;
        this.block = new byte[blockSize];

        Arrays.fill(this.block, Byte.MIN_VALUE);
    }

    @Override
    public byte[] getBytes() {
        byte[] result = Arrays.copyOf(block, block.length);

        for (int i = 0; i < block.length; ++i) {
            if (block[i] == Byte.MAX_VALUE) {
                block[i] = Byte.MIN_VALUE;
            } else {
                block[i]++;
                break;
            }
        }

        return result;
    }

    @Override
    public int getBlockSize() {
        return blockSize;
    }
}
