package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class CreditCard {

    private long id;
    private long cardNumber;
    private Instant validThru;
    private int cvv;
    private long userId;

    public CreditCard() {
    }

    public CreditCard(long id, long cardNumber, Instant validThru, int cvv, long userId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.validThru = validThru;
        this.cvv = cvv;
        this.userId = userId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Instant getValidThru() {
        return validThru;
    }

    public void setValidThru(Instant validThru) {
        this.validThru = validThru;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
