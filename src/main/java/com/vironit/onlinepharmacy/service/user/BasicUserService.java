package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.dto.UserData;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.UserServiceException;
import com.vironit.onlinepharmacy.util.Converter;

import java.util.Collection;

public class BasicUserService implements UserService {

    private final UserDao userDAO;
    private final Converter<User,UserData> userDataToUserConverter;

    public BasicUserService(UserDao userDAO, Converter<User, UserData> userDataToUserConverter) {
        this.userDAO = userDAO;
        this.userDataToUserConverter = userDataToUserConverter;
    }

    @Override
    public long add(UserData userData) {
        return userDAO.add(userDataToUserConverter.convert(userData));
    }

    @Override
    public User get(long id) {
        return userDAO.get(id).orElseThrow(() -> new UserServiceException("Can't get user. User with id " + id + " not found."));
    }

    @Override
    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public void update(UserData userData) {
        userDAO.update(userDataToUserConverter.convert(userData));
    }

    @Override
    public void remove(long id) {
        userDAO.remove(id);
    }
}
