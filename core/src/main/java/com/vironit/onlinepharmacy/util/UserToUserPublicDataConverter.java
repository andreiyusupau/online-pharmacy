package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserPublicData;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserPublicDataConverter implements Converter<UserPublicData, User> {
    @Override
    public UserPublicData convert(User user) {
        return new UserPublicData(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
