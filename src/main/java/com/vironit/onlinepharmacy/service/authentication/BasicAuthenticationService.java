package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserLoginData;
import com.vironit.onlinepharmacy.dto.UserPublicData;
import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.Converter;


public class BasicAuthenticationService implements AuthenticationService<UserData,UserPublicData,UserLoginData> {

    private final UserDao userDao;
    private final PasswordHasher passwordHasher;
    private final Converter<UserPublicData, User> userToUserPublicParametersConverter;
    private final Converter<User, UserData> userRegisterParametersToUserConverter;

    public BasicAuthenticationService(UserDao userDao, PasswordHasher passwordHasher,
                                      Converter<UserPublicData, User> userToUserPublicParametersConverter,
                                      Converter<User, UserData> userRegisterParametersToUserConverter) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
        this.userToUserPublicParametersConverter = userToUserPublicParametersConverter;
        this.userRegisterParametersToUserConverter = userRegisterParametersToUserConverter;
    }

    @Override
    public long register(UserData userData) {
        User user = userRegisterParametersToUserConverter.convert(userData);
        String email = userData.getEmail();
        if (userDao.getByEmail(email).isEmpty()) {
            String password = userData.getPassword();
            String hashedPassword = passwordHasher.hashPassword(password);
            user.setPassword(hashedPassword);
            return userDao.add(user);
        } else {
            throw new AuthenticationServiceException("User with email " + email + " already exists.");
        }
    }
    @Override
    public UserPublicData login(UserLoginData userLoginData) {
        String email = userLoginData.getEmail();
        User user = userDao.getByEmail(email).orElseThrow(() -> new AuthenticationServiceException("User with email " + email + " does not exist."));
        String password = userLoginData.getPassword();
        if (passwordHasher.validatePassword(password, user.getPassword())) {
            return userToUserPublicParametersConverter.convert(user);
        } else {
            throw new AuthenticationServiceException("Wrong password for user " + email);
        }
    }

}
