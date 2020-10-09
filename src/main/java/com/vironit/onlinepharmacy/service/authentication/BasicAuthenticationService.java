package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.authentication.exception.LoginException;
import com.vironit.onlinepharmacy.service.authentication.exception.RegistrationException;
import com.vironit.onlinepharmacy.util.UserParser;


public class BasicAuthenticationService implements AuthenticationService {

    private final UserDao userDao;
    private final PasswordHasher passwordHasher;

    public BasicAuthenticationService(UserDao userDao, PasswordHasher passwordHasher) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public UserPublicParameters login(UserLoginParameters userLoginParameters) throws LoginException {
        String email = userLoginParameters.getEmail();
        User user = userDao.getByEmail(email).orElseThrow(() -> new LoginException("User with email " + email + " does not exist."));
        String password = userLoginParameters.getPassword();
        if (passwordHasher.validatePassword(password, user.getPassword())) {
            return UserParser.userPublicParametersFromUser(user);
        } else {
            throw new LoginException("Wrong password for user " + email);
        }
    }

    @Override
    public long register(UserRegisterParameters userRegisterParameters) throws RegistrationException {
        User user = UserParser.userFromUserRegisterParameters(userRegisterParameters);
        String email = userRegisterParameters.getEmail();
        if (userDao.getByEmail(email).isEmpty()) {
            String password = userRegisterParameters.getPassword();
            String hashedPassword = passwordHasher.hashPassword(password);
            user.setPassword(hashedPassword);
            return userDao.add(user);
        } else {
            throw new RegistrationException("User with email " + email + " already exists.");
        }
    }
}
