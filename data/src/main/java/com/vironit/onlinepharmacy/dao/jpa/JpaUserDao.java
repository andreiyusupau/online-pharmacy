package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Optional;

public class JpaUserDao implements UserDao {

    @PersistenceContext
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
        entityManager.persist(user);
        entityManager.getTransaction()
                .commit();
        return user.getId();
    }

    @Override
    public Optional<User> get(long id) {
        User user=entityManager.find(User.class,id);
        entityManager.detach(user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        User user=entityManager.find(User.class,id);
        entityManager.remove(user);
        entityManager.getTransaction()
                .commit();
        return true;
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
