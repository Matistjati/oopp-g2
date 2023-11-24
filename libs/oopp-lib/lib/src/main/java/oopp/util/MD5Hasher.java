package oopp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hasher {
    private static final MessageDigest digest;
    static {
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String createHash(byte[] bytes) {
        return new String(digest.digest(bytes), StandardCharsets.UTF_8);
    }
}