package com.github.elementbound.hashcompress.enhash;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static org.apache.commons.codec.digest.MessageDigestAlgorithms.MD5;

public class HashCompressFactory {
    public static HashCompressImpl getHashCompress(int blockSize) {
        MessageDigest digest = getDigest(MD5);

        DigestUtils digestUtils = new DigestUtils(digest);
        RandomBlockSupplier blockSupplier = new RandomBlockSupplier(blockSize, new Random());

        Enhash enhash = new Enhash(digestUtils);
        Dehash dehash = new Dehash(blockSupplier, digestUtils);

        return new HashCompressImpl(blockSize, digest.getDigestLength(), digestUtils, enhash, dehash);
    }

    private static MessageDigest getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
