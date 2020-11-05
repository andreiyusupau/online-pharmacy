package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class RecipeDto {

    private final String description;
    private final int quantity;
    private final long productId;
    private final Instant validThru;
    private final long orderPositionId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RecipeDto(@JsonProperty("description")String description,
                     @JsonProperty("quantity")int quantity,
                     @JsonProperty("productId")long productId,
                     @JsonProperty("validThru")Instant validThru,
                     @JsonProperty("orderPositionId")long orderPositionId) {
        this.description = description;
        this.quantity = quantity;
        this.productId = productId;
        this.validThru = validThru;
        this.orderPositionId = orderPositionId;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getProductId() {
        return productId;
    }

    public Instant getValidThru() {
        return validThru;
    }

    public long getOrderPositionId() {
        return orderPositionId;
    }
}
