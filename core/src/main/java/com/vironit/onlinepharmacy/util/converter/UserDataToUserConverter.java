package com.vironit.onlinepharmacy.util.converter;

import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDataToUserConverter implements Converter<User, UserDto> {
    @Override
    public User convert(UserDto userDto) {
        User user=new User();
        user.setFirstName(userDto.getFirstName());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setEmail(userDto.getEmail());
        return user;
    }
}
