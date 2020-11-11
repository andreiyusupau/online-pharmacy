package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

public class PositionDto {

    @Positive
    private final long productId;
    @Min(1)
    private final int quantity;
    private long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PositionDto(@JsonProperty("productId") long productId,
                       @JsonProperty("quantity") int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
