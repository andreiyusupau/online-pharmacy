package com.vironit.onlinepharmacy.service.creditcard;

import com.vironit.onlinepharmacy.dao.CreditCardDao;
import com.vironit.onlinepharmacy.dto.CreditCardDto;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.CreditCardServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicCreditCardService implements CreditCardService {

    private final CreditCardDao creditCardDao;
    private final Converter<CreditCard, CreditCardDto> creditCardDataToCreditCardConverter;

    public BasicCreditCardService(CreditCardDao creditCardDao, Converter<CreditCard, CreditCardDto> creditCardDataToCreditCardConverter) {
        this.creditCardDao = creditCardDao;
        this.creditCardDataToCreditCardConverter = creditCardDataToCreditCardConverter;
    }

    @Override
    public long add(CreditCardDto creditCardDto) {
        User user=new User();
        user.setId(creditCardDto.getOwnerId());
        CreditCard creditCard=creditCardDataToCreditCardConverter.convert(creditCardDto);
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
