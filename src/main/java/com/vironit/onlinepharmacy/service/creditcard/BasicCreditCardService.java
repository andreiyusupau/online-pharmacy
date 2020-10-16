package com.vironit.onlinepharmacy.service.creditcard;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.service.exception.CreditCardServiceException;

import java.util.Collection;

public class BasicCreditCardService implements CreditCardService {

    private final CreditCardDao creditCardDao;

    public BasicCreditCardService(CreditCardDao creditCardDao) {
        this.creditCardDao = creditCardDao;
    }

    @Override
    public long add(CreditCard creditCard) {
        return creditCardDao.add(creditCard);
    }

    @Override
    public CreditCard get(long id) {
        return creditCardDao.get(id)
                .orElseThrow(() -> new CreditCardServiceException("Can't get credit card. Credit card with id " + id + " not found."));
    }

    @Override
    public Collection<CreditCard> getAll() {
        return creditCardDao.getAll();
    }

    @Override
    public void remove(long id) {
        creditCardDao.remove(id);
    }
}
