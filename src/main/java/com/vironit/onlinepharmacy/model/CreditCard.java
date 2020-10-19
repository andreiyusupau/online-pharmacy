package com.vironit.onlinepharmacy.model;

import java.time.LocalDate;
import java.util.Objects;

public class CreditCard {

    private long id;
    private String cardNumber;
    private String ownerName;
    private LocalDate validThru;
    private int cvv;
    private User owner;

    public CreditCard() {
    }

    public CreditCard(long id, String cardNumber,String ownerName, LocalDate validThru, int cvv, User owner) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.ownerName=ownerName;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDate getValidThru() {
        return validThru;
    }

    public void setValidThru(LocalDate validThru) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return id == that.id &&
                cvv == that.cvv &&
                cardNumber.equals(that.cardNumber) &&
                ownerName.equals(that.ownerName) &&
                validThru.equals(that.validThru) &&
                owner.equals(that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber, ownerName, validThru, cvv, owner);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", validThru=" + validThru +
                ", cvv=" + cvv +
                ", owner=" + owner +
                '}';
    }
}
