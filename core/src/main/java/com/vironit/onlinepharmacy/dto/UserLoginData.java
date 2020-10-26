package com.vironit.onlinepharmacy.dto;

import java.util.Objects;

public class UserLoginData {

    private final String email;
    private final String password;

    public UserLoginData(String email, String password) {
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
        UserLoginData that = (UserLoginData) o;
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
