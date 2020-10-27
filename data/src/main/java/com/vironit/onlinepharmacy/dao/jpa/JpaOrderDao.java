package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

public class JpaOrderDao implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean update(Order order) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(order);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(Order order) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(order);
        entityManager.getTransaction()
                .commit();
        return order.getId();
    }

    @Override
    public Optional<Order> get(long id) {
        Order order =entityManager.find(Order.class,id);
        entityManager.detach(order);
        return Optional.of(order);
    }

    @Override
    public Collection<Order> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root=criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        Order order=entityManager.find(Order.class,id);
        entityManager.remove(order);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long getTotalElements() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public Collection<Order> getPage(int currentPage, int pageLimit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root=criteriaQuery.from(Order.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((currentPage - 1) * pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }

    @Override
    public boolean addAll(Collection<Order> orders) {
        entityManager.getTransaction()
                .begin();
        for (Order order:orders){
            entityManager.persist(order);
        }
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public Collection<Order> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root=criteriaQuery.from(Order.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user")
                        .get("id"),id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Order> criteriaDelete = criteriaBuilder.createCriteriaDelete(Order.class);
        Root<Order> root= criteriaDelete.from(Order.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("user")
                .get("id"),id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate()>0;
    }
}
