package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProductCategoryDao;
import com.vironit.onlinepharmacy.model.ProductCategory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaProductCategoryDao implements ProductCategoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean update(ProductCategory productCategory) {
        entityManager.merge(productCategory);
        return true;
    }

    @Transactional
    @Override
    public long add(ProductCategory productCategory) {
        entityManager.persist(productCategory);
        return productCategory.getId();
    }

    @Override
    public Optional<ProductCategory> get(long id) {
        ProductCategory productCategory = entityManager.find(ProductCategory.class, id);
        entityManager.detach(productCategory);
        return Optional.ofNullable(productCategory);
    }

    @Override
    public Collection<ProductCategory> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductCategory> criteriaQuery = criteriaBuilder.createQuery(ProductCategory.class);
        Root<ProductCategory> root = criteriaQuery.from(ProductCategory.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean remove(long id) {
        ProductCategory productCategory = entityManager.find(ProductCategory.class, id);
        entityManager.remove(productCategory);
        return true;
    }
}
