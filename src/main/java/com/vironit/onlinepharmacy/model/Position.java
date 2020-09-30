package com.vironit.onlinepharmacy.model;

public class Position {

    private long id;
    private Product product;
    private int amount;

    public Position() {
    }

    public Position(long id, Product product, int amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
