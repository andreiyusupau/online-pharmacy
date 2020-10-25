package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserPublicData;
import com.vironit.onlinepharmacy.model.User;

public class UserToUserPublicParametersConverter implements Converter<UserPublicData, User> {
    @Override
    public UserPublicData convert(User user) {
        return new UserPublicData(user.getId(), user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
