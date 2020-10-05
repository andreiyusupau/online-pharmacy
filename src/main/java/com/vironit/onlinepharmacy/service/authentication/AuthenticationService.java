package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.service.authentication.exception.LoginException;
import com.vironit.onlinepharmacy.service.authentication.exception.RegistrationException;

public interface AuthenticationService {

    UserPublicParameters login(UserLoginParameters userLoginParameters) throws LoginException;

    long register(UserRegisterParameters userRegisterParameters) throws RegistrationException;
}
