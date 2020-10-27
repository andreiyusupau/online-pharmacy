package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.model.ProcurementPosition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

public class JpaProcurementPositionDao implements ProcurementPositionDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean update(ProcurementPosition procurementposition) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(procurementposition);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(ProcurementPosition procurementposition) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(procurementposition);
        entityManager.getTransaction()
                .commit();
        return procurementposition.getId();
    }

    @Override
    public Optional<ProcurementPosition> get(long id) {
        ProcurementPosition procurementposition =entityManager.find(ProcurementPosition.class,id);
        entityManager.detach(procurementposition);
        return Optional.of(procurementposition);
    }

    @Override
    public Collection<ProcurementPosition> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcurementPosition> criteriaQuery = criteriaBuilder.createQuery(ProcurementPosition.class);
        Root<ProcurementPosition> root=criteriaQuery.from(ProcurementPosition.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        ProcurementPosition procurementposition=entityManager.find(ProcurementPosition.class,id);
        entityManager.remove(procurementposition);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public boolean addAll(Collection<ProcurementPosition> procurementpositions) {
        entityManager.getTransaction()
                .begin();
        for (ProcurementPosition procurementposition:procurementpositions){
            entityManager.persist(procurementposition);
        }
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public Collection<ProcurementPosition> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProcurementPosition> criteriaQuery = criteriaBuilder.createQuery(ProcurementPosition.class);
        Root<ProcurementPosition> root=criteriaQuery.from(ProcurementPosition.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("procurement")
                        .get("id"),id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<ProcurementPosition> criteriaDelete = criteriaBuilder.createCriteriaDelete(ProcurementPosition.class);
        Root<ProcurementPosition> root= criteriaDelete.from(ProcurementPosition.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("procurement")
                .get("id"),id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate()>0;
    }
}
