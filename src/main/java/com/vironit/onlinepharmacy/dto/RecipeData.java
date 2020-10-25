package com.vironit.onlinepharmacy.dto;

import java.time.Instant;

public class RecipeData {

    private  final String description;
    private final int quantity;
    private final long productId;
    private final Instant validThru;
    private final long orderPositionId;

    public RecipeData(String description, int quantity, long productId, Instant validThru, long orderPositionId) {
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
