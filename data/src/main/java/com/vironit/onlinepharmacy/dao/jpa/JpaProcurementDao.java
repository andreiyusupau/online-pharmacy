package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.model.Procurement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

public class JpaProcurementDao implements ProcurementDao {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public boolean update(Procurement procurement) {
        entityManager.getTransaction()
                .begin();
        entityManager.merge(procurement);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long add(Procurement procurement) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(procurement);
        entityManager.getTransaction()
                .commit();
        return procurement.getId();
    }

    @Override
    public Optional<Procurement> get(long id) {
        Procurement procurement =entityManager.find(Procurement.class,id);
        entityManager.detach(procurement);
        return Optional.of(procurement);
    }

    @Override
    public Collection<Procurement> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Procurement> criteriaQuery = criteriaBuilder.createQuery(Procurement.class);
        Root<Procurement> root=criteriaQuery.from(Procurement.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        Procurement procurement=entityManager.find(Procurement.class,id);
        entityManager.remove(procurement);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public long getTotalElements() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Procurement> root = criteriaQuery.from(Procurement.class);
        criteriaQuery.select(criteriaBuilder.count(root));
        return entityManager.createQuery(criteriaQuery)
                .getSingleResult();
    }

    @Override
    public Collection<Procurement> getPage(int currentPage, int pageLimit) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Procurement> criteriaQuery = criteriaBuilder.createQuery(Procurement.class);
        Root<Procurement> root=criteriaQuery.from(Procurement.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((currentPage - 1) * pageLimit)
                .setMaxResults(pageLimit)
                .getResultList();
    }

    @Override
    public boolean addAll(Collection<Procurement> procurements) {
        entityManager.getTransaction()
                .begin();
        for (Procurement procurement:procurements){
            entityManager.persist(procurement);
        }
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public Collection<Procurement> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Procurement> criteriaQuery = criteriaBuilder.createQuery(Procurement.class);
        Root<Procurement> root=criteriaQuery.from(Procurement.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user")
                        .get("id"),id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Procurement> criteriaDelete = criteriaBuilder.createCriteriaDelete(Procurement.class);
        Root<Procurement> root= criteriaDelete.from(Procurement.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("user")
                .get("id"),id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate()>0;
    }
}
