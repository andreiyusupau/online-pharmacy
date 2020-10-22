package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserPublicParameters;
import com.vironit.onlinepharmacy.model.User;

public class UserToUserPublicParametersConverter implements Converter<UserPublicParameters, User> {
    @Override
    public UserPublicParameters convert(User user) {
        return new UserPublicParameters(user.getId(), user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
