package com.vironit.onlinepharmacy.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue
    private long id;
    private String description;
    private int quantity;
    @ManyToOne
    private Product product;
    private Instant validThru;
    @OneToOne
    private OrderPosition orderPosition;

    public Recipe() {
    }

    public Recipe(long id, String description, int quantity, Product product, Instant validThru, OrderPosition orderPosition) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.product = product;
        this.validThru = validThru;
        this.orderPosition = orderPosition;
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

    public OrderPosition getOrderPosition() {
        return orderPosition;
    }

    public void setOrderPosition(OrderPosition orderPosition) {
        this.orderPosition = orderPosition;
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
                orderPosition.equals(recipe.orderPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, quantity, product, validThru, orderPosition);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", product=" + product +
                ", validThru=" + validThru +
                ", orderPosition=" + orderPosition +
                '}';
    }
}
