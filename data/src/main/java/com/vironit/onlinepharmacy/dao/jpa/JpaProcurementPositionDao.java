package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
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
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.ProcurementPositionRepository}
 */
@Deprecated
public class JpaProcurementPositionDao implements ProcurementPositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean update(ProcurementPosition procurementposition) {
        entityManager.merge(procurementposition);
        return true;
    }

    @Transactional
    @Override
    public long add(ProcurementPosition procurementposition) {
        entityManager.persist(procurementposition);
        return procurementposition.getId();
    }

    @Override
    public Optional<ProcurementPosition> get(long id) {
        ProcurementPosition procurementposition = entityManager.find(ProcurementPosition.class, id);
        entityManager.detach(procurementposition);
        return Optional.ofNullable(procurementposition);
    }

    @Override
    public Collection<ProcurementPosition> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcurementPosition> criteriaQuery = criteriaBuilder.createQuery(ProcurementPosition.class);
        Root<ProcurementPosition> root = criteriaQuery.from(ProcurementPosition.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean remove(long id) {
        ProcurementPosition procurementposition = entityManager.find(ProcurementPosition.class, id);
        entityManager.remove(procurementposition);
        return true;
    }

    @Transactional
    @Override
    public boolean addAll(Collection<ProcurementPosition> procurementpositions) {
        for (ProcurementPosition procurementposition : procurementpositions) {
            entityManager.persist(procurementposition);
        }
        return true;
    }

    @Override
    public Collection<ProcurementPosition> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcurementPosition> criteriaQuery = criteriaBuilder.createQuery(ProcurementPosition.class);
        Root<ProcurementPosition> root = criteriaQuery.from(ProcurementPosition.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("procurement")
                        .get("id"), id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<ProcurementPosition> criteriaDelete = criteriaBuilder.createCriteriaDelete(ProcurementPosition.class);
        Root<ProcurementPosition> root = criteriaDelete.from(ProcurementPosition.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("procurement")
                .get("id"), id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate() > 0;
    }
}
