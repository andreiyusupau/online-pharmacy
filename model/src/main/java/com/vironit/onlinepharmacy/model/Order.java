package com.vironit.onlinepharmacy.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends Operation {

    @Enumerated(value = EnumType.STRING)
    @Column(name="status")
    private OrderStatus status;

    public Order() {
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
