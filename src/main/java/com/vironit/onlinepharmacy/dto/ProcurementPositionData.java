package com.vironit.onlinepharmacy.dto;

public class ProcurementPositionData {

    private final long productId;
    private final int quantity;

    public ProcurementPositionData(long productId, int quantity) {
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
