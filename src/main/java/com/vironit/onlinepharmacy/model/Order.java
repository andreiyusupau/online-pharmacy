package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class Order extends Operation {

    private OrderStatus status;

    public Order() {
        super();
    }

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
}
