package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public class CreditCardDto {

    private final String cardNumber;
    private final String ownerName;
    @JsonDeserialize(as=LocalDate.class)
    private final LocalDate validThru;
    private final int cvv;
    private final long ownerId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreditCardDto(@JsonProperty("cardNumber")String cardNumber,
                         @JsonProperty("ownerName")String ownerName,
                         @JsonProperty("validThru")LocalDate validThru,
                         @JsonProperty("cvv")int cvv,
                         @JsonProperty("ownerId")long ownerId) {
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
