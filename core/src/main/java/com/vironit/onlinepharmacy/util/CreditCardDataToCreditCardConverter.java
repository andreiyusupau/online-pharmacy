package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.CreditCardDto;
import com.vironit.onlinepharmacy.model.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class CreditCardDataToCreditCardConverter implements Converter<CreditCard, CreditCardDto> {
    @Override
    public CreditCard convert(CreditCardDto creditCardDto) {
        CreditCard creditCard=new CreditCard();
        creditCard.setCardNumber(creditCardDto.getCardNumber());
        creditCard.setValidThru(creditCardDto.getValidThru());
        creditCard.setCvv(creditCardDto.getCvv());
        creditCard.setOwnerName(creditCardDto.getOwnerName());
        return creditCard;
    }
}
