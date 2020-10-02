package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.AuthenticationDAO;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.exception.LoginException;
import com.vironit.onlinepharmacy.exception.RegistrationException;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.util.UserParser;


public class BasicAuthenticationService implements AuthenticationService {

    private final AuthenticationDAO userDAO;
    private final PasswordHasher passwordHasher;

    public BasicAuthenticationService(AuthenticationDAO userDAO, PasswordHasher passwordHasher) {
        this.userDAO = userDAO;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UserPublicParameters login(UserLoginParameters userLoginParameters) throws LoginException {
        String email= userLoginParameters.getEmail();
        User user=userDAO.getByEmail(email).orElseThrow(()->new LoginException("User with email "+email+" does not exist."));
        String password=userLoginParameters.getPassword();
        String hashedPassword= passwordHasher.hashPassword(password);
        if(passwordHasher.comparePasswords(user.getPassword(),hashedPassword)) {
            return UserParser.userPublicParametersFromUser(user);
        }else {
            throw new LoginException("Wrong password for user "+email);
        }

    }

    @Override
    public long register(UserRegisterParameters userRegisterParameters) throws RegistrationException {
        User user=UserParser.userFromUserRegisterParameters(userRegisterParameters);
        String email=userRegisterParameters.getEmail();
        if(userDAO.getByEmail(email).isEmpty()){
            String password=userRegisterParameters.getPassword();
            String hashedPassword= passwordHasher.hashPassword(password);
            user.setPassword(hashedPassword);
            return userDAO.add(user);
        }else {
            throw new RegistrationException("User with email "+email+" already exists.");
        }
    }
}
