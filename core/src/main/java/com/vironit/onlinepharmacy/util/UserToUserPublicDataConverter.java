package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.vo.UserPublicVo;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserPublicDataConverter implements Converter<UserPublicVo, User> {
    @Override
    public UserPublicVo convert(User user) {
        return new UserPublicVo(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
