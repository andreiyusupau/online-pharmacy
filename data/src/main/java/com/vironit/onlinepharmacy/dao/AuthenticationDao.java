package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.User;

import java.util.Optional;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.UserRepository}
 */
@Deprecated
public interface AuthenticationDao {

    Optional<User> getByEmail(String email);
}
