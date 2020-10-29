package com.vironit.onlinepharmacy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "order_positions")
public class OrderPosition extends Position {

    @ManyToOne
    @JoinColumn(name = "order_id",nullable = false)
    private Order order;

    public OrderPosition() {

    }

    public OrderPosition(long id, int quantity, Product product, Order order) {
        super(id, quantity, product);
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderPosition)) return false;
        if (!super.equals(o)) return false;
        OrderPosition that = (OrderPosition) o;
        return order.equals(that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), order);
    }

    @Override
    public String toString() {
        return "OrderPosition{" +
                "order=" + order +
                '}';
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
