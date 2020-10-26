package com.vironit.onlinepharmacy.dto;

import java.time.LocalDate;

public class CreditCardData {

    private  final String cardNumber;
    private final String ownerName;
    private final LocalDate validThru;
    private final int cvv;
    private final long ownerId;

    public CreditCardData(String cardNumber, String ownerName, LocalDate validThru, int cvv, long ownerId) {
        this.cardNumber = cardNumber;
        this.ownerName = ownerName;
        this.validThru = validThru;
        this.cvv = cvv;
        this.ownerId = ownerId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public int getCvv() {
        return cvv;
    }

    public long getOwnerId() {
        return ownerId;
    }
}
