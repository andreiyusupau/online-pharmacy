package com.vironit.onlinepharmacy.security.exception;

public class PasswordHashException extends RuntimeException {
    public PasswordHashException(String message) {
        super(message);
    }

    public PasswordHashException(String message, Throwable cause) {
        super(message, cause);
    }
}
