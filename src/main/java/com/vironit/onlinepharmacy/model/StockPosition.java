package com.vironit.onlinepharmacy.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "stock_positions")
public class StockPosition extends Position {

    private int reservedQuantity;

    public StockPosition() {
    }

    public StockPosition(long id, int quantity, Product product, int reservedQuantity) {
        super(id, quantity, product);
        this.reservedQuantity = reservedQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity) {
        this.reservedQuantity = reservedQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockPosition)) return false;
        if (!super.equals(o)) return false;
        StockPosition that = (StockPosition) o;
        return reservedQuantity == that.reservedQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), reservedQuantity);
    }

    @Override
    public String toString() {
        return "StockPosition{" +
                "reservedQuantity=" + reservedQuantity +
                '}';
    }
}
