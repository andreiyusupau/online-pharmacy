package com.vironit.onlinepharmacy.dto;

public class PositionData {

    private final long productId;
    private final int quantity;

    public PositionData(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
