package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class UserLoginDto {
    @NotBlank
    private final String email;
    @NotBlank
    private final String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public UserLoginDto(@JsonProperty("email") String email,
                        @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
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
        UserLoginDto that = (UserLoginDto) o;
        return email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "UserLoginParameters{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
