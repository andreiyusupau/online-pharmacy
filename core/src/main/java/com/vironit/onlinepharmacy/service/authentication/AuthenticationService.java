package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.model.User;

public interface AuthenticationService<T, S, U> {
    long register(T t);

    S login(U u);

    User getByEmail(String email);
}
