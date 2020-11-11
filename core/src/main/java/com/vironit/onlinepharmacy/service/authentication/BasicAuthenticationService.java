package com.vironit.onlinepharmacy.service.authentication;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.dto.UserLoginDto;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.security.PasswordHasher;
import com.vironit.onlinepharmacy.service.exception.AuthenticationServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class BasicAuthenticationService implements AuthenticationService<UserDto, UserPublicVo, UserLoginDto> {

    private final UserDao userDao;
    private final PasswordHasher passwordHasher;
    private final Converter<UserPublicVo, User> userToUserPublicDataConverter;
    private final Converter<User, UserDto> userDataToUserConverter;

    public BasicAuthenticationService(UserDao userDao, PasswordHasher passwordHasher,
                                      Converter<UserPublicVo, User> userToUserPublicDataConverter,
                                      Converter<User, UserDto> userDataToUserConverter) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
        this.userToUserPublicDataConverter = userToUserPublicDataConverter;
        this.userDataToUserConverter = userDataToUserConverter;
    }

    @Override
    public long register(UserDto userDto) {
        User user = userDataToUserConverter.convert(userDto);
        String email = userDto.getEmail();
        if (userDao.getByEmail(email)
                .isEmpty()) {
            String password = userDto.getPassword();
            String hashedPassword = passwordHasher.hashPassword(password);
            user.setPassword(hashedPassword);
            return userDao.add(user);
        } else {
            throw new AuthenticationServiceException("User with email " + email + " already exists.");
        }
    }

    @Override
    public UserPublicVo login(UserLoginDto userLoginDto) {
        String email = userLoginDto.getEmail();
        User user = userDao.getByEmail(email).orElseThrow(() -> new AuthenticationServiceException("User with email " + email + " does not exist."));
        String password = userLoginDto.getPassword();
        if (passwordHasher.validatePassword(password, user.getPassword())) {
            System.out.println(user);
            return userToUserPublicDataConverter.convert(user);
        } else {
            throw new AuthenticationServiceException("Wrong password for user " + email);
        }
    }
}
