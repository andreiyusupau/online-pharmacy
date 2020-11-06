package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserPublicDto;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserPublicDataConverter implements Converter<UserPublicDto, User> {
    @Override
    public UserPublicDto convert(User user) {
        return new UserPublicDto(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
