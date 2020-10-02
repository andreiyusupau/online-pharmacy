package com.vironit.onlinepharmacy.model;

import java.util.Objects;

public class Position {

    private long id;
    private int quantity;
    private Product product;
    private Operation operation;

    public Position() {
    }

    public Position(long id, int quantity, Product product, Operation operation) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.operation = operation;
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

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return id == position.id &&
                quantity == position.quantity &&
                product.equals(position.product) &&
                operation.equals(position.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, product, operation);
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product +
                ", operation=" + operation +
                '}';
    }
}