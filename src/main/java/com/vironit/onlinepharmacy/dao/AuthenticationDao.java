package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

import java.util.Optional;

public interface AuthenticationDao {

    Optional<User> getByEmail(String email);

}
