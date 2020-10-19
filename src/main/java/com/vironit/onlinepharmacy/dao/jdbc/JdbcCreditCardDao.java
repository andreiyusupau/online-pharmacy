package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.model.CreditCard;

import java.util.Collection;
import java.util.Optional;

public class JdbcCreditCardDao implements CreditCardDao {

    @Override
    public long add(CreditCard creditCard) {
        return 0;
    }

    @Override
    public Optional<CreditCard> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<CreditCard> getAll() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public boolean addAll(Collection<CreditCard> t) {
        return false;
    }

    @Override
    public Collection<CreditCard> getAllByOwnerId(long id) {
        return null;
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return false;
    }
}
