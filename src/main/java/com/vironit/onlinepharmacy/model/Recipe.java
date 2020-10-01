package com.vironit.onlinepharmacy.model;

public class Recipe {

    private long id;
    private String description;
    private int quantity;
    private long productId;

    public Recipe() {
    }

    public Recipe(long id, String description, int quantity, long productId) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.productId = productId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
