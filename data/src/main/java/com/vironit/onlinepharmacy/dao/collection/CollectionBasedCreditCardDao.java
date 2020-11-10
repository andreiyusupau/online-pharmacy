package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.CreditCard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.dao.jdbc.JdbcCreditCardDao}
 */
@Deprecated
public class CollectionBasedCreditCardDao implements CreditCardDao {

    private final IdGenerator idGenerator;
    private final Collection<CreditCard> creditCardList = new ArrayList<>();

    public CollectionBasedCreditCardDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(CreditCard creditCard) {
        long id = idGenerator.getNextId();
        creditCard.setId(id);
        boolean successfulAdd = creditCardList.add(creditCard);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<CreditCard> get(long id) {
        return creditCardList.stream()
                .filter(creditCard -> creditCard.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<CreditCard> getAll() {
        return creditCardList;
    }

    @Override
    public boolean remove(long id) {
        return creditCardList.removeIf(creditCard -> creditCard.getId() == id);
    }

    @Override
    public boolean addAll(Collection<CreditCard> creditCards) {
        creditCards.forEach(this::add);
        return true;
    }

    @Override
    public Collection<CreditCard> getAllByOwnerId(long id) {
        return creditCardList.stream()
                .filter(creditCard -> creditCard.getOwner().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return creditCardList.removeIf(creditCard -> creditCard.getOwner().getId() == id);
    }
}
