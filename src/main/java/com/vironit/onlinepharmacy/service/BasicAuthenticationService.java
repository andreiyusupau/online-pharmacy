package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.AuthenticationDAO;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;

import javax.security.auth.login.LoginException;

public class BasicAuthenticationService implements AuthenticationService {

    private final AuthenticationDAO userDAO;
    private final PasswordHasher passwordHasher;

    public BasicAuthenticationService(AuthenticationDAO userDAO, PasswordHasher passwordHasher) {
        this.userDAO = userDAO;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UserPublicParameters login(UserLoginParameters userLoginParameters) {

        String email= userLoginParameters.getEmail();
       //TODO:Exception
        User user=userDAO.getByEmail().orElseThrow();
        String password=userLoginParameters.getPassword();
        String hashedPassword= passwordHasher.hashPassword(password);
        if(passwordHasher.comparePasswords(user.getPassword(),hashedPassword)) {
            return new UserPublicParameters()
        }else {
            throw new LoginException();
        }

    }

    @Override
    public long register(UserRegisterParameters userRegisterParameters) {
        return -1;
    }
}
