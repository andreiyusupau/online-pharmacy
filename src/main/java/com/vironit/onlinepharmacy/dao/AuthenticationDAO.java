package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

import java.util.Optional;

public interface AuthenticationDAO {

    Optional<User> getByEmail(String email);

}
