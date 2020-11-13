package com.vironit.onlinepharmacy.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PBKDF2PasswordEncoderTest {

    private PBKDF2PasswordEncoder pbkdf2PasswordEncoder;
    private String password;

    @BeforeEach
    void set() {
        pbkdf2PasswordEncoder = new PBKDF2PasswordEncoder();
        password = "password123";
    }

    @Test
    void hashPasswordNotEqualForSamePasswords() {
        String firstPasswordHash = pbkdf2PasswordEncoder.encode(password);
        String secondPasswordHash = pbkdf2PasswordEncoder.encode(password);

        Assertions.assertNotEquals(firstPasswordHash, secondPasswordHash);
    }

    @Test
    void validatePasswordReturnsTrue() {
        String passwordHash = pbkdf2PasswordEncoder.encode(password);

        boolean validationResult = pbkdf2PasswordEncoder.matches(password, passwordHash);

        Assertions.assertTrue(validationResult);
    }

    @Test
    void validatePasswordReturnsFalse() {
        String wrongPassword = "WronGPassWord9542";
        String passwordHash = pbkdf2PasswordEncoder.encode(password);
        boolean validationResult = pbkdf2PasswordEncoder.matches(wrongPassword, passwordHash);

        Assertions.assertFalse(validationResult);
    }
}
