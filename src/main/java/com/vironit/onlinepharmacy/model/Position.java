package com.vironit.onlinepharmacy.model;

public class Position {

    private long id;
    private int amount;
    private long product_id;
    private long operation_id;

    public Position() {
    }

    public Position(long id, int amount, long product_id, long operation_id) {
        this.id = id;
        this.amount = amount;
        this.product_id = product_id;
        this.operation_id = operation_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public long getOperation_id() {
        return operation_id;
    }

    public void setOperation_id(long operation_id) {
        this.operation_id = operation_id;
    }
}
