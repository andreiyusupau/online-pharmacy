package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class CreditCard {

    private long id;
    private long cardNumber;
    private Instant validThru;
    private int cvv;
    private User owner;

    public CreditCard() {
    }

    public CreditCard(long id, long cardNumber, Instant validThru, int cvv, User owner) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.validThru = validThru;
        this.cvv = cvv;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
