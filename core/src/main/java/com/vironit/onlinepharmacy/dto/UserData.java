package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;


public class UserData {

    private final long id;
    private final String firstName;
    private final String middleName;
    private final String lastName;

    private final LocalDate dateOfBirth;
    private final String email;
    private final String password;
    private final String confirmPassword;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserData(@JsonProperty("id") long id, @JsonProperty("firstName")String firstName, @JsonProperty("middleName")String middleName, @JsonProperty("lastName")String lastName, @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") @JsonProperty("dateOfBirth")LocalDate dateOfBirth, @JsonProperty("email")String email, @JsonProperty("password") String password, @JsonProperty("confirmPassword")String confirmPassword) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserData)) return false;
        UserData that = (UserData) o;
        return firstName.equals(that.firstName) &&
                middleName.equals(that.middleName) &&
                lastName.equals(that.lastName) &&
                dateOfBirth.equals(that.dateOfBirth) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                confirmPassword.equals(that.confirmPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName, dateOfBirth, email, password, confirmPassword);
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
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

