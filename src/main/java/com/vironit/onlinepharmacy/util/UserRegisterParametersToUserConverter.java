package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;

public class UserRegisterParametersToUserConverter implements Converter<User, UserRegisterParameters> {

    @Override
    public User convert(UserRegisterParameters userRegisterParameters) {
        User user = new User();
        user.setFirstName(userRegisterParameters.getFirstName());
        user.setMiddleName(userRegisterParameters.getMiddleName());
        user.setLastName(userRegisterParameters.getLastName());
        user.setDateOfBirth(userRegisterParameters.getDateOfBirth());
        user.setEmail(userRegisterParameters.getEmail());
        user.setRole(Role.CONSUMER);
        return user;
    }
}
