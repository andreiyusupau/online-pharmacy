package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaProductDao implements ProductDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean update(Product product) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(product);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(Product product) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(product);
        entityManager.getTransaction()
                .commit();
        return product.getId();
    }

    @Override
    public Optional<Product> get(long id) {
        Product product = entityManager.find(Product.class, id);
        entityManager.detach(product);
        return Optional.of(product);
    }

    @Override
    public Collection<Product> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        Product product = entityManager.find(Product.class, id);
        entityManager.remove(product);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long getTotalElements() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public Collection<Product> getPage(int currentPage, int pageLimit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((currentPage - 1) * pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }
}
