package com.vironit.onlinepharmacy.model;

import java.time.Instant;
import java.util.Objects;

public class Order extends Operation {

    private OrderStatus status;

    public Order(long id, Instant date, User owner, OrderStatus status) {
        super(id, date, owner);
        this.status = status;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), status);
    }
}
