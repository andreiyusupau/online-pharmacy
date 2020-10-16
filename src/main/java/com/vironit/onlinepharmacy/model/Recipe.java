package com.vironit.onlinepharmacy.model;

import java.time.Instant;
import java.util.Objects;

//TODO:add service
public class Recipe {

    private long id;
    private String description;
    private int quantity;
    private Product product;
    private Instant validThru;
    private OperationPosition position;

    public Recipe() {
    }

    public Recipe(long id, String description, int quantity, Product product, Instant validThru, OperationPosition position) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.product = product;
        this.validThru = validThru;
        this.position = position;
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

    public Instant getValidThru() {
        return validThru;
    }

    public void setValidThru(Instant validThru) {
        this.validThru = validThru;
    }

    public OperationPosition getPosition() {
        return position;
    }

    public void setPosition(OperationPosition position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe)) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id &&
                quantity == recipe.quantity &&
                description.equals(recipe.description) &&
                product.equals(recipe.product) &&
                validThru.equals(recipe.validThru) &&
                position.equals(recipe.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantity, product, validThru, position);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", product=" + product +
                ", validThru=" + validThru +
                ", position=" + position +
                '}';
    }
}
