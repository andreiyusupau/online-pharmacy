package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CreditCardDto {
    @NotBlank
    @CreditCardNumber
    private final String cardNumber;
    @NotBlank
    @Size(min = 2, max = 26)
    private final String ownerName;
    @JsonDeserialize(as = LocalDate.class)
    @FutureOrPresent
    private final LocalDate validThru;
    @Size(min = 3, max = 4)
    private final String cvv;
    @Positive
    private final long ownerId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CreditCardDto(@JsonProperty("cardNumber") String cardNumber,
                         @JsonProperty("ownerName") String ownerName,
                         @JsonProperty("validThru") LocalDate validThru,
                         @JsonProperty("cvv") String cvv,
                         @JsonProperty("ownerId") long ownerId) {
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

    public String getCvv() {
        return cvv;
    }

    public long getOwnerId() {
        return ownerId;
    }
}
