package com.vironit.onlinepharmacy.service.authentication;

public interface AuthenticationService<T,S,U> {
    long register(T t);
    S login(U u);
}
