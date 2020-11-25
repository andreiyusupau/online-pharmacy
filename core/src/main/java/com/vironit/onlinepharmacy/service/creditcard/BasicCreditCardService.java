package com.vironit.onlinepharmacy.service.creditcard;

import com.vironit.onlinepharmacy.dto.CreditCardDto;
import com.vironit.onlinepharmacy.model.CreditCard;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.repository.CreditCardRepository;
import com.vironit.onlinepharmacy.service.exception.CreditCardServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicCreditCardService implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final Converter<CreditCard, CreditCardDto> creditCardDataToCreditCardConverter;

    public BasicCreditCardService(CreditCardRepository creditCardRepository, Converter<CreditCard, CreditCardDto> creditCardDataToCreditCardConverter) {
        this.creditCardRepository = creditCardRepository;
        this.creditCardDataToCreditCardConverter = creditCardDataToCreditCardConverter;
    }

    @Override
    public long add(CreditCardDto creditCardDto) {
        User user = new User();
        user.setId(creditCardDto.getOwnerId());
        CreditCard creditCard = creditCardDataToCreditCardConverter.convert(creditCardDto);
        creditCard.setOwner(user);
        return creditCardRepository.save(creditCard)
                .getId();
    }

    @Override
    public CreditCard get(long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new CreditCardServiceException("Can't get credit card. Credit card with id " + id + " not found."));
    }

    @Override
    public Collection<CreditCard> getAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public void remove(long id) {
        creditCardRepository.deleteById(id);
    }
}
