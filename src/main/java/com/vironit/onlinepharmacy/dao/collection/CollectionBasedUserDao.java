package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedUserDao implements UserDao {

    private final IdGenerator idGenerator;
    private final Collection<User> userList = new ArrayList<>();

    public CollectionBasedUserDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(User user) {
        long id = idGenerator.getNextId();
        user.setId(id);
        userList.add(user);
        System.out.println(userList.toString());
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
