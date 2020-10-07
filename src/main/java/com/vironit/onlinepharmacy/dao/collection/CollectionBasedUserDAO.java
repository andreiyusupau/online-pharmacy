package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.UserDAO;
import com.vironit.onlinepharmacy.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CollectionBasedUserDAO implements UserDAO {

    private final List<User> userList = new ArrayList<>();
    private long currentId = 0;

    @Override
    public long add(User user) {
        user.setId(currentId);
        currentId++;
        userList.add(user);
        return user.getId();
    }

    @Override
    public Optional<User> get(long id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<User> getAll() {
        return userList;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public Optional<User> getByEmail(String email) {
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
