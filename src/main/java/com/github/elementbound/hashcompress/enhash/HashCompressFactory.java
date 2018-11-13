package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class HashCompressFactory {
    public static HashCompressImpl getHashCompress(int blockSize) {
        MessageDigest digest = getDigest(MD5);

        DigestUtils digestUtils = new DigestUtils(digest);
        BlockSupplier blockSupplier = getBlockSupplier(blockSize);

        Enhash enhash = new Enhash(digestUtils);
        Dehash dehash = new Dehash(blockSupplier, digestUtils);

        return new HashCompressImpl(digest.getDigestLength(), blockSize, digestUtils, enhash, dehash);
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static BlockSupplier getBlockSupplier(int blockSize) {
        // return new RandomBlockSupplier(blockSize, new Random());
        return new IterativeBlockSupplier(blockSize);
    }
}
