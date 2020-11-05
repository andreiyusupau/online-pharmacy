package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionDto {

    private long id;
    private final long productId;
    private final int quantity;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PositionDto(@JsonProperty("productId")long productId,
                       @JsonProperty("quantity")int quantity) {
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
