package com.vironit.onlinepharmacy.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UserRegisterParameters {

    private final String firstName;
    private final String middleName;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final String email;
    private final String password;

    public UserRegisterParameters(String firstName, String middleName, String lastName, LocalDate dateOfBirth, String email, String password) {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterParameters that = (UserRegisterParameters) o;
        return firstName.equals(that.firstName) &&
                middleName.equals(that.middleName) &&
                lastName.equals(that.lastName) &&
                dateOfBirth.equals(that.dateOfBirth) &&
                email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, dateOfBirth, email, password);
    }

    @Override
    public String toString() {
        return "UserRegisterParameters{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

