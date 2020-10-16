package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.dto.UserRegisterParameters;
import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.model.User;

public class UserParser {

    public static UserPublicParameters userPublicParametersFromUser(User user) {
        return new UserPublicParameters(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(),
                user.getDateOfBirth(), user.getEmail(), user.getRole());
    }

    public static User userFromUserRegisterParameters(UserRegisterParameters userRegisterParameters) {
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
