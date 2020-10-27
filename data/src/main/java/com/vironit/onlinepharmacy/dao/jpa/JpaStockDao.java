package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.StockDao;
import com.vironit.onlinepharmacy.model.StockPosition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

public class JpaStockDao implements StockDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<StockPosition> getByProductId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockPosition> criteriaQuery = criteriaBuilder.createQuery(StockPosition.class);
        Root<StockPosition> root=criteriaQuery.from(StockPosition.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("product")
                        .get("id"),id));
        return Optional.of(entityManager.createQuery(criteriaQuery)
                .getSingleResult());
    }

    @Override
    public boolean update(StockPosition stockPosition) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(stockPosition);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(StockPosition stockPosition) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(stockPosition);
        entityManager.getTransaction()
                .commit();
        return stockPosition.getId();
    }

    @Override
    public Optional<StockPosition> get(long id) {
        StockPosition stockPosition=entityManager.find(StockPosition.class,id);
        entityManager.detach(stockPosition);
        return Optional.of(stockPosition);
    }

    @Override
    public Collection<StockPosition> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockPosition> criteriaQuery = criteriaBuilder.createQuery(StockPosition.class);
        Root<StockPosition> root=criteriaQuery.from(StockPosition.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        StockPosition stockPosition=entityManager.find(StockPosition.class,id);
        entityManager.remove(stockPosition);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long getTotalElements() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<StockPosition> root = criteriaQuery.from(StockPosition.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public Collection<StockPosition> getPage(int currentPage, int pageLimit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<StockPosition> criteriaQuery = criteriaBuilder.createQuery(StockPosition.class);
        Root<StockPosition> root=criteriaQuery.from(StockPosition.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((currentPage - 1) * pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }
}
