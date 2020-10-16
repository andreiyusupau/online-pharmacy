package com.vironit.onlinepharmacy.service.user;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.user.exception.UserServiceException;

import java.util.Collection;

public class BasicUserService implements UserService {

    private final UserDao userDAO;

    public BasicUserService(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public long add(User user) {
        return userDAO.add(user);
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
    public void update(User user) {

    }

    @Override
    public void remove(long id) {

    }
}
