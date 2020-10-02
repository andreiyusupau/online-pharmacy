package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;

import java.util.Optional;

public interface AuthenticationService {

    UserPublicParameters login(UserLoginParameters userLoginParameters);

    long register(UserRegisterParameters userRegisterParameters);
}
