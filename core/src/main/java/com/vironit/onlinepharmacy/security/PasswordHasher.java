package com.vironit.onlinepharmacy.security;

public interface PasswordHasher {
    String hashPassword(String password);

    boolean validatePassword(String originalPassword, String storedPassword);
}
