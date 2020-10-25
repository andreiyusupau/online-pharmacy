package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;

public class UserRegisterParametersToUserConverter implements Converter<User, UserData> {

    @Override
    public User convert(UserData userData) {
        User user = new User();
        user.setFirstName(userData.getFirstName());
        user.setMiddleName(userData.getMiddleName());
        user.setLastName(userData.getLastName());
        user.setDateOfBirth(userData.getDateOfBirth());
        user.setEmail(userData.getEmail());
        user.setRole(Role.CONSUMER);
        return user;
    }
}
