package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserLoginParameters;
import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.Converter;


public class BasicAuthenticationService implements AuthenticationService {

    private final UserDao userDao;
    private final PasswordHasher passwordHasher;
    private final Converter<UserPublicParameters, User> userToUserPublicParametersConverter;
    private final Converter<User, UserRegisterParameters> userRegisterParametersToUserConverter;

    public BasicAuthenticationService(UserDao userDao, PasswordHasher passwordHasher,
                                      Converter<UserPublicParameters, User> userToUserPublicParametersConverter,
                                      Converter<User, UserRegisterParameters> userRegisterParametersToUserConverter) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
        this.userToUserPublicParametersConverter = userToUserPublicParametersConverter;
        this.userRegisterParametersToUserConverter = userRegisterParametersToUserConverter;
    }

    @Override
    public UserPublicParameters login(UserLoginParameters userLoginParameters) {
        String email = userLoginParameters.getEmail();
        User user = userDao.getByEmail(email).orElseThrow(() -> new AuthenticationServiceException("User with email " + email + " does not exist."));
        String password = userLoginParameters.getPassword();
        if (passwordHasher.validatePassword(password, user.getPassword())) {
            return userToUserPublicParametersConverter.convert(user);
        } else {
            throw new AuthenticationServiceException("Wrong password for user " + email);
        }
    }

    @Override
    public long register(UserRegisterParameters userRegisterParameters) {
        User user = userRegisterParametersToUserConverter.convert(userRegisterParameters);
        String email = userRegisterParameters.getEmail();
        if (userDao.getByEmail(email).isEmpty()) {
            String password = userRegisterParameters.getPassword();
            String hashedPassword = passwordHasher.hashPassword(password);
            user.setPassword(hashedPassword);
            return userDao.add(user);
        } else {
            throw new AuthenticationServiceException("User with email " + email + " already exists.");
        }
    }
}