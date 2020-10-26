package com.vironit.onlinepharmacy.service.creditcard;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dto.CreditCardData;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.CreditCardServiceException;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.util.Converter;

import java.util.Collection;

public class BasicCreditCardService implements CreditCardService {

    private final CreditCardDao creditCardDao;
    private final UserService userService;
    private final Converter<CreditCard,CreditCardData> creditCardDataToCreditCardConverter;

    public BasicCreditCardService(CreditCardDao creditCardDao, UserService userService, Converter<CreditCard, CreditCardData> creditCardDataToCreditCardConverter) {
        this.creditCardDao = creditCardDao;
        this.userService = userService;
        this.creditCardDataToCreditCardConverter = creditCardDataToCreditCardConverter;
    }

    @Override
    public long add(CreditCardData creditCardData) {
        User user=new User();
        user.setId(creditCardData.getOwnerId());
        CreditCard creditCard=creditCardDataToCreditCardConverter.convert(creditCardData);
        creditCard.setOwner(user);
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
