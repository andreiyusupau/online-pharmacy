package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.OrderPositionDao;
import com.vironit.onlinepharmacy.model.OrderPosition;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.OrderPositionRepository}
 */
@Deprecated
public class JpaOrderPositionDao implements OrderPositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean update(OrderPosition orderPosition) {
        entityManager.merge(orderPosition);
        return true;
    }

    @Transactional
    @Override
    public long add(OrderPosition orderPosition) {
        entityManager.persist(orderPosition);
        return orderPosition.getId();
    }

    @Override
    public Optional<OrderPosition> get(long id) {
        OrderPosition orderPosition = entityManager.find(OrderPosition.class, id);
        entityManager.detach(orderPosition);
        return Optional.ofNullable(orderPosition);
    }

    @Override
    public Collection<OrderPosition> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderPosition> criteriaQuery = criteriaBuilder.createQuery(OrderPosition.class);
        Root<OrderPosition> root = criteriaQuery.from(OrderPosition.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean remove(long id) {
        OrderPosition orderPosition = entityManager.find(OrderPosition.class, id);
        entityManager.remove(orderPosition);
        return true;
    }

    @Transactional
    @Override
    public boolean addAll(Collection<OrderPosition> orderPositions) {
        for (OrderPosition orderPosition : orderPositions) {
            entityManager.persist(orderPosition);
        }
        return true;
    }

    @Override
    public Collection<OrderPosition> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderPosition> criteriaQuery = criteriaBuilder.createQuery(OrderPosition.class);
        Root<OrderPosition> root = criteriaQuery.from(OrderPosition.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("order")
                        .get("id"), id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<OrderPosition> criteriaDelete = criteriaBuilder.createCriteriaDelete(OrderPosition.class);
        Root<OrderPosition> root = criteriaDelete.from(OrderPosition.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("order")
                .get("id"), id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate() > 0;
    }
}
