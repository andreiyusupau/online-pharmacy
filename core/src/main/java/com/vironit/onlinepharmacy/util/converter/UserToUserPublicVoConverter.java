package com.vironit.onlinepharmacy.util.converter;

import com.vironit.onlinepharmacy.vo.UserPublicVo;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserToUserPublicVoConverter implements Converter<UserPublicVo, User> {
    @Override
    public UserPublicVo convert(User user) {
        return new UserPublicVo(user.getId(), user.getFirstName(), user.getMiddleName(), user.getLastName(), user.getDateOfBirth(), user.getEmail(), user.getRole());
    }
}
