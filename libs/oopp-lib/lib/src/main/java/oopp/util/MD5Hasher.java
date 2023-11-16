package oopp.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Hasher {
    public static String createHash(String input)
    {
        return createHash(input.getBytes(StandardCharsets.UTF_8));
    }
    public static String createHash(byte[] bytes)
    {
        String output = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("MD5");
            output = new String(digest.digest(bytes), StandardCharsets.UTF_8);
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }

        return output;
    }

}