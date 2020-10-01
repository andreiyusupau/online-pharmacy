package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.User;

public interface AuthenticationService {

    boolean login(User user);
    boolean register(User user);
}
