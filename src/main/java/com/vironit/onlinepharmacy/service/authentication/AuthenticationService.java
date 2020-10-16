package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;

public interface AuthenticationService {

    UserPublicParameters login(UserLoginParameters userLoginParameters);

    long register(UserRegisterParameters userRegisterParameters);
}
