package com.vironit.onlinepharmacy.model;

public class Position {

    private long id;
    private int quantity;
    private long productId;
    private long operationId;

    public Position() {
    }

    public Position(long id, int quantity, long productId, long operationId) {
        this.id = id;
        this.quantity = quantity;
        this.productId = productId;
        this.operationId = operationId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getOperationId() {
        return operationId;
    }

    public void setOperationId(long operationId) {
        this.operationId = operationId;
    }
}
