package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vironit.onlinepharmacy.dto.validation.PasswordMatches;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@PasswordMatches
public class UserDto {

    @NotBlank
    @Size(min = 2, max = 50)
    private final String firstName;
    @NotBlank
    @Size(min = 2, max = 50)
    private final String middleName;
    @NotBlank
    @Size(min = 2, max = 50)
    private final String lastName;
    @JsonDeserialize(as = LocalDate.class)
    @NotNull
    @Past
    private final LocalDate dateOfBirth;
    @NotBlank
    @Email
    private final String email;
    @NotBlank
    @Size(min = 8, max = 30)
    private final String password;
    @NotBlank
    private final String confirmPassword;
    private long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDto(@JsonProperty("firstName") String firstName,
                   @JsonProperty("middleName") String middleName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                   @JsonProperty("email") String email,
                   @JsonProperty("password") String password,
                   @JsonProperty("confirmPassword") String confirmPassword) {
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

    public void setId(long id) {
        this.id = id;
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
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
                firstName.equals(userDto.firstName) &&
                middleName.equals(userDto.middleName) &&
                lastName.equals(userDto.lastName) &&
                dateOfBirth.equals(userDto.dateOfBirth) &&
                email.equals(userDto.email) &&
                password.equals(userDto.password) &&
                confirmPassword.equals(userDto.confirmPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, middleName, lastName, dateOfBirth, email, password, confirmPassword);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}

