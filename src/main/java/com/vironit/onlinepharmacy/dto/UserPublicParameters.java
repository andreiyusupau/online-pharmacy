package com.vironit.onlinepharmacy.dto;

import com.vironit.onlinepharmacy.model.Role;

import java.time.Instant;

public class UserPublicParameters {

    private final long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final Instant dateOfBirth;
    private final String email;
    private final String password;
    private final Role role;

    public UserPublicParameters(long id, String firstName, String middleName, String lastName, Instant dateOfBirth, String email, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public long getId() {
        return id;
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

    public Role getRole() {
        return role;
    }
}
