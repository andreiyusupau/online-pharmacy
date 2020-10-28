package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.model.CreditCard;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaCreditCardDao implements CreditCardDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long add(CreditCard creditCard) {
        entityManager.getTransaction()
                .begin();
        entityManager.persist(creditCard);
        entityManager.getTransaction()
                .commit();
        return creditCard.getId();
    }

    @Override
    public Optional<CreditCard> get(long id) {
        CreditCard creditCard = entityManager.find(CreditCard.class, id);
        entityManager.detach(creditCard);
        return Optional.of(creditCard);
    }

    @Override
    public Collection<CreditCard> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> root = criteriaQuery.from(CreditCard.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean remove(long id) {
        entityManager.getTransaction()
                .begin();
        CreditCard creditCard = entityManager.find(CreditCard.class, id);
        entityManager.remove(creditCard);
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public boolean addAll(Collection<CreditCard> creditCards) {
        entityManager.getTransaction()
                .begin();
        for (CreditCard creditCard : creditCards) {
            entityManager.persist(creditCard);
        }
        entityManager.getTransaction()
                .commit();
        return true;
    }

    @Override
    public Collection<CreditCard> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> root = criteriaQuery.from(CreditCard.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("user")
                        .get("id"), id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<CreditCard> criteriaDelete = criteriaBuilder.createCriteriaDelete(CreditCard.class);
        Root<CreditCard> root = criteriaDelete.from(CreditCard.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("user")
                .get("id"), id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate() > 0;
    }
}
