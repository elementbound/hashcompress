package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class to compress a single block of data.
 */
public class Enhash {
    private final DigestUtils digestUtils;

    public Enhash(DigestUtils digestUtils) {
        this.digestUtils = digestUtils;
    }

    /**
     * Compress a single block of data.
     *
     * @param data to compress
     * @return compressed data
     */
    public byte[] consume(byte[] data) {
        return digestUtils.digest(data);
    }
}
