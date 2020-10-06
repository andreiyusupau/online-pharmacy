package com.vironit.onlinepharmacy.model;

public class Position {

    private long id;
    private int quantity;
    private Product product;

    public Position() {
    }

    public Position(long id, int quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (id != position.id) return false;
        if (quantity != position.quantity) return false;
        return product.equals(position.product);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + quantity;
        result = 31 * result + product.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Position{");
        sb.append("id=").append(id);
        sb.append(", quantity=").append(quantity);
        sb.append(", product=").append(product);
        sb.append('}');
        return sb.toString();
    }
}