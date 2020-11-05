package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserDto;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.UserServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicUserService implements UserService {

    private final UserDao userDAO;
    private final Converter<User, UserDto> userDataToUserConverter;

    public BasicUserService(UserDao userDAO, Converter<User, UserDto> userDataToUserConverter) {
        this.userDAO = userDAO;
        this.userDataToUserConverter = userDataToUserConverter;
    }

    @Override
    public long add(UserDto userDto) {
        return userDAO.add(userDataToUserConverter.convert(userDto));
    }

    @Override
    public User get(long id) {
        return userDAO.get(id)
                .orElseThrow(() -> new UserServiceException("Can't get user. User with id " + id + " not found."));
    }

    @Override
    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void update(UserDto userDto) {
        userDAO.update(userDataToUserConverter.convert(userDto));
    }

    @Override
    public void remove(long id) {
        userDAO.remove(id);
    }
}
