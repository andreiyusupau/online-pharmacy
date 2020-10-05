package com.vironit.onlinepharmacy.model;

import java.util.Objects;

public class Position {

    private long id;
    private int quantity;
    private Product product;
    private Order order;

    public Position() {
    }

    public Position(long id, int quantity, Product product, Order order) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.order = order;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOperation() {
        return order;
    }

    public void setOperation(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return id == position.id &&
                quantity == position.quantity &&
                product.equals(position.product) &&
                order.equals(position.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product, order);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                ", order=" + order +
                '}';
    }
}