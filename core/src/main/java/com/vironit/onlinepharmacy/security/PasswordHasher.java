package com.vironit.onlinepharmacy.security;
@Deprecated
public interface PasswordHasher {
    String hashPassword(String password);

    boolean validatePassword(String originalPassword, String storedPassword);
}
