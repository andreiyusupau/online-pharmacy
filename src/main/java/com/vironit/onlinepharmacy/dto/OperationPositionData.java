package com.vironit.onlinepharmacy.dto;

public class OperationPositionData {

    private final long productId;
    private final int quantity;

    public OperationPositionData(long productId, int quantity) {
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
