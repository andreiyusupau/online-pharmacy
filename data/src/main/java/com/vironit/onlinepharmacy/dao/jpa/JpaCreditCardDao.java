package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.model.CreditCard;
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
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.CreditCardRepository}
 */
@Deprecated
public class JpaCreditCardDao implements CreditCardDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public long add(CreditCard creditCard) {
        entityManager.persist(creditCard);
        return creditCard.getId();
    }

    @Override
    public Optional<CreditCard> get(long id) {
        CreditCard creditCard = entityManager.find(CreditCard.class, id);
        entityManager.detach(creditCard);
        return Optional.ofNullable(creditCard);
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

    @Transactional
    @Override
    public boolean remove(long id) {
        CreditCard creditCard = entityManager.find(CreditCard.class, id);
        entityManager.remove(creditCard);
        return true;
    }

    @Transactional
    @Override
    public boolean addAll(Collection<CreditCard> creditCards) {
        for (CreditCard creditCard : creditCards) {
            entityManager.persist(creditCard);
        }
        return true;
    }

    @Override
    public Collection<CreditCard> getAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CreditCard> criteriaQuery = criteriaBuilder.createQuery(CreditCard.class);
        Root<CreditCard> root = criteriaQuery.from(CreditCard.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("owner")
                        .get("id"), id));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean removeAllByOwnerId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<CreditCard> criteriaDelete = criteriaBuilder.createCriteriaDelete(CreditCard.class);
        Root<CreditCard> root = criteriaDelete.from(CreditCard.class);
        criteriaDelete.where(criteriaBuilder.equal(root.get("owner")
                .get("id"), id));
        return entityManager.createQuery(criteriaDelete)
                .executeUpdate() > 0;
    }
}
