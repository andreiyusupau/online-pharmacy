package com.vironit.onlinepharmacy.security;

import com.vironit.onlinepharmacy.security.exception.PasswordHashException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

@Component
public class PBKDF2PasswordEncoder implements PasswordEncoder {

    public static final int ITERATIONS = 65536;
    public static final int KEY_LENGTH = 128;

    private byte[] getSalt() {
        SecureRandom sr;
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashException("No such secure random algorithm.", e);
        }
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

//    @Override
//    public String hashPassword(String password) {
//        int iterations = ITERATIONS;
//        char[] chars = password.toCharArray();
//        byte[] salt = getSalt();
//        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, KEY_LENGTH);
//        byte[] hash = getHash(spec);
//        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
//    }

//    @Override
//    public boolean validatePassword(String originalPassword, String storedPassword) {
//        String[] parts = storedPassword.split(":");
//        int iterations = Integer.parseInt(parts[0]);
//        byte[] salt = fromHex(parts[1]);
//        byte[] hash = fromHex(parts[2]);
//
//        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
//
//        byte[] testHash = getHash(spec);
//
//        int diff = hash.length ^ testHash.length;
//        for (int i = 0; i < hash.length && i < testHash.length; i++) {
//            diff |= hash[i] ^ testHash[i];
//        }
//        return diff == 0;
//    }

    private byte[] getHash(PBEKeySpec spec) {

        SecretKeyFactory skf;
        try {
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashException("SecretKeyFactory not found.", e);
        }
        byte[] hash;
        try {
            hash = skf.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new PasswordHashException("Invalid key specification.", e);
        }
        return hash;
    }

    @Override
    public String encode(CharSequence charSequence) {
        int iterations = ITERATIONS;
        char[] chars = charSequence.toString()
                .toCharArray();
        byte[] salt = getSalt();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, KEY_LENGTH);
        byte[] hash = getHash(spec);
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String[] parts = s.split(":");
        int iterations = Integer.parseInt(parts[0]);
        byte[] salt = fromHex(parts[1]);
        byte[] hash = fromHex(parts[2]);

        PBEKeySpec spec = new PBEKeySpec(charSequence.toString()
                .toCharArray(), salt, iterations, hash.length * 8);

        byte[] testHash = getHash(spec);

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }
}
