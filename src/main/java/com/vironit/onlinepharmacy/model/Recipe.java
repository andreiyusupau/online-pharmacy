package com.vironit.onlinepharmacy.model;

public class Recipe {

    private long id;
    private String description;
    private Product product;
    private int amount;

    public Recipe() {
    }

    public Recipe(long id, String description, Product product, int amount) {
        this.id = id;
        this.description = description;
        this.product = product;
        this.amount = amount;
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
