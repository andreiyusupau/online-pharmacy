package com.vironit.onlinepharmacy.model;

import java.util.Objects;

public class Recipe {

    private long id;
    private String description;
    private int quantity;
    private Product product;

    public Recipe() {
    }

    public Recipe(long id, String description, int quantity, Product product) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id &&
                quantity == recipe.quantity &&
                description.equals(recipe.description) &&
                product.equals(recipe.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantity, product);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", product=" + product +
                '}';
    }
}
