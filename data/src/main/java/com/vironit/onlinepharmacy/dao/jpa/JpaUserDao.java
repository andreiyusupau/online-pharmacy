package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

public class JpaUserDao implements UserDao {

    private EntityManager entityManager;

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public long add(User user) {
        return 0;
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.of(entityManager.find(User.class,id));
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public int getTotalElements() {
        return 0;
    }

    @Override
    public Collection<User> getPage(int currentPage, int pageLimit) {
        return null;
    }
}
