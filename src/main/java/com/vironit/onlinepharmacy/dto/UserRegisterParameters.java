package com.vironit.onlinepharmacy.dto;

import java.time.Instant;

public class UserRegisterParameters {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final Instant dateOfBirth;
    private final String email;
    private final String password;

    public UserRegisterParameters(String firstName, String middleName, String lastName, Instant dateOfBirth, String email, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

