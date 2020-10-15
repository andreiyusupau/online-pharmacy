package com.vironit.onlinepharmacy.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PBKDF2PasswordHasherTest {

    private PBKDF2PasswordHasher pbkdf2PasswordHasher;
    private String password;

    @BeforeEach
    void set(){
        pbkdf2PasswordHasher=new PBKDF2PasswordHasher();
        password= "password123";
           }

    @Test
    void testHashPasswordNotEqualForSamePasswords(){
        String firstPasswordHash= pbkdf2PasswordHasher.hashPassword(password);
        String secondPasswordHash= pbkdf2PasswordHasher.hashPassword(password);

        Assertions.assertNotEquals(firstPasswordHash,secondPasswordHash);
    }

    @Test
    void testValidatePasswordReturnsTrue(){
        String passwordHash= pbkdf2PasswordHasher.hashPassword(password);

        boolean validationResult=pbkdf2PasswordHasher.validatePassword(password,passwordHash);

        Assertions.assertTrue(validationResult);
    }

    @Test
    void testValidatePasswordReturnsFalse(){
        String wrongPassword = "WronGPassWord9542";
        String passwordHash= pbkdf2PasswordHasher.hashPassword(password);
        boolean validationResult=pbkdf2PasswordHasher.validatePassword(wrongPassword,passwordHash);

        Assertions.assertFalse(validationResult);
    }
}
