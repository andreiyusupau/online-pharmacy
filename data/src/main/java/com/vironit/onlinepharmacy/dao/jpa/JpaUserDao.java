package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.UserDao;
import com.vironit.onlinepharmacy.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaUserDao implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getByEmail(String email) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("email"), email));
        return Optional.of(entityManager.createQuery(criteriaQuery)
                .getSingleResult());
    }

    @Override
    public boolean update(User user) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(user);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(User user) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(user);
        entityManager.getTransaction()
                .commit();
        return user.getId();
    }

    @Override
    public Optional<User> get(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.detach(user);
        return Optional.of(user);
    }

    @Override
    public Collection<User> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long getTotalElements() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public Collection<User> getPage(int currentPage, int pageLimit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((currentPage - 1) * pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }
}
