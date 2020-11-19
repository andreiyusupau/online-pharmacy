package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vironit.onlinepharmacy.dto.validation.PasswordMatches;
import com.vironit.onlinepharmacy.model.Role;

import javax.validation.constraints.*;
import java.time.LocalDate;

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
    //TODO:
    private Role role;
    private long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserDto(@JsonProperty("firstName") String firstName,
                   @JsonProperty("middleName") String middleName,
                   @JsonProperty("lastName") String lastName,
                   @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                   @JsonProperty("email") String email,
                   @JsonProperty("password") String password,
                   @JsonProperty("confirmPassword") String confirmPassword,
                   @JsonProperty("role") Role role) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role=role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (id != userDto.id) return false;
        if (!firstName.equals(userDto.firstName)) return false;
        if (!middleName.equals(userDto.middleName)) return false;
        if (!lastName.equals(userDto.lastName)) return false;
        if (!dateOfBirth.equals(userDto.dateOfBirth)) return false;
        if (!email.equals(userDto.email)) return false;
        if (!password.equals(userDto.password)) return false;
        if (!confirmPassword.equals(userDto.confirmPassword)) return false;
        return role == userDto.role;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + dateOfBirth.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + confirmPassword.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", role=" + role +
                ", id=" + id +
                '}';
    }
}

