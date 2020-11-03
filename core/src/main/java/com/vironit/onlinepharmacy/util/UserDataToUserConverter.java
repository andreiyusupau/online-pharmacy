package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataToUserConverter implements Converter<User, UserData> {
    @Override
    public User convert(UserData userData) {
        User user=new User();
        user.setFirstName(userData.getFirstName());
        user.setMiddleName(userData.getMiddleName());
        user.setLastName(userData.getLastName());
        user.setDateOfBirth(userData.getDateOfBirth());
        user.setEmail(userData.getEmail());
        return user;
    }
}
