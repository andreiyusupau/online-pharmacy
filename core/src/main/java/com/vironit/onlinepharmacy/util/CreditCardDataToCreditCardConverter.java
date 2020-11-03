package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.CreditCardData;
import com.vironit.onlinepharmacy.model.CreditCard;
import org.springframework.stereotype.Component;

@Component
public class CreditCardDataToCreditCardConverter implements Converter<CreditCard, CreditCardData> {
    @Override
    public CreditCard convert(CreditCardData creditCardData) {
        CreditCard creditCard=new CreditCard();
        creditCard.setCardNumber(creditCardData.getCardNumber());
        creditCard.setValidThru(creditCardData.getValidThru());
        creditCard.setCvv(creditCardData.getCvv());
        creditCard.setOwnerName(creditCardData.getOwnerName());
        return creditCard;
    }
}
