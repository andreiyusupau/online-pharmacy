package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.UserServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class BasicUserService implements UserService {
    private final UserDao userDAO;
    private final Converter<User, UserDto> userDtoToUserConverter;
    private final Converter<UserPublicVo, User> userToUserPublicVoConverter;

    public BasicUserService(UserDao userDAO, Converter<User, UserDto> userDtoToUserConverter, Converter<UserPublicVo, User> userToUserPublicVoConverter) {
        this.userDAO = userDAO;
        this.userDtoToUserConverter = userDtoToUserConverter;
        this.userToUserPublicVoConverter = userToUserPublicVoConverter;
    }

    @Override
    public long add(UserDto userDto) {
        return userDAO.add(userDtoToUserConverter.convert(userDto));
    }

    @Override
    public UserPublicVo get(long id) {
        User user=userDAO.get(id)
                .orElseThrow(() -> new UserServiceException("Can't get user. User with id " + id + " not found."));
        return userToUserPublicVoConverter.convert(user);
    }

    @Override
    public Collection<UserPublicVo> getAll() {
        return userDAO.getAll()
                .stream()
                .map(userToUserPublicVoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public void update(UserDto userDto) {
        userDAO.update(userDtoToUserConverter.convert(userDto));
    }

    @Override
    public void remove(long id) {
        userDAO.remove(id);
    }
}
