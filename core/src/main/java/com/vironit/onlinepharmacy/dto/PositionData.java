package com.vironit.onlinepharmacy.dto;

public class PositionData {

    private final long id;
    private final long productId;
    private final int quantity;

    public PositionData(long id, long productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
