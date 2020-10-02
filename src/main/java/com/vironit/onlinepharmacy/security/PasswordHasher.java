package com.vironit.onlinepharmacy.security;

public interface PasswordHasher {
    String hashPassword(String password);
    boolean comparePasswords(String originalPassword, String comparedPassword);
}
