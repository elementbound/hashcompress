package com.github.elementbound.hashcompress.enhash.supplier;

/**
 * <p>Class to supply a block of bytes.
 * <p>Each instance has a block size as its state
 */
public interface BlockSupplier {
    /**
     * <p>Get a block of bytes.
     * <p>Each call will return a new block with different contents.
     *
     * @return block of bytes
     */
    byte[] getBytes();

    /**
     * Get block size.
     *
     * @return block size
     */
    int getBlockSize();
}
