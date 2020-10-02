package com.vironit.onlinepharmacy.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface PasswordHasher {
    String hashPassword(String password);
    boolean validatePassword(String originalPassword, String storedPassword);
}
