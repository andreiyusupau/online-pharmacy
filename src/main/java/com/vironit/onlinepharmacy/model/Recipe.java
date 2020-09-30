package com.vironit.onlinepharmacy.model;

public class Recipe {

    private long id;
    private String description;
    private int amount;
    private long product_id;

    public Recipe() {
    }

    public Recipe(long id, String description, int amount, long product_id) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.product_id = product_id;
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
}
