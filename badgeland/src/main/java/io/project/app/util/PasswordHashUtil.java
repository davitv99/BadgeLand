package io.project.app.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashUtil {


    private PasswordHashUtil() {
    }

    public static String hashPassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String hashPasswordOld(String text) {
        return toHexString(md5(text));
    }

    public static String toHexString(byte[] bytes) {
        if (bytes == null) {
            throw new IllegalArgumentException("byte array must not be null");
        }
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            hex.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
            hex.append(Character.forDigit((bytes[i] & 0X0F), 16));
        }
        return hex.toString();
    }
    //here we go:))

    public static byte[] fromHexString(String hex) {
        if (hex == null) {
            throw new IllegalArgumentException("Hex string must not be null");
        }
        if (hex.length() % 2 == 1) {
            throw new IllegalArgumentException("Hex string length is not even : " + hex.length());
        }
        int index = 0;
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            char chigh = hex.charAt(index++);
            int high = Character.digit(chigh, 16);
            if (high == -1) {
                throw new IllegalArgumentException("Hex string contains a bad char : " + chigh);
            }
            char clow = hex.charAt(index++);
            int low = Character.digit(clow, 16);
            if (low == -1) {
                throw new IllegalArgumentException("Hex string contains a bad char : " + clow);
            }
            byte value = (byte) ((high << 4) + low);
            bytes[i] = value;
        }
        return bytes;
    }

    public static byte[] md5(String text) {
        // arguments check
        if (text == null) {
            throw new NullPointerException("null text");
        }

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(text.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            // LOG.error("Cannot find MD5 algorithm", e);
            throw new RuntimeException("Cannot find MD5 algorithm");
        }
    }
}
