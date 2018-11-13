package com.github.elementbound.hashcompress.enhash;

public interface BlockSupplier {
    byte[] getBytes();

    int getBlockSize();
}
