package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.CreditCardData;
import com.vironit.onlinepharmacy.model.CreditCard;

public class CreditCardDataToCreditCardConverter implements Converter<CreditCard, CreditCardData> {

    @Override
    public CreditCard convert(CreditCardData creditCardData) {
        return new CreditCard(0, creditCardData.getCardNumber(), creditCardData.getOwnerName(), creditCardData.getValidThru(),creditCardData.getCvv(),null);
    }
}