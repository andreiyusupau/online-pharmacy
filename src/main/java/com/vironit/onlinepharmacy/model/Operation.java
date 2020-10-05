package com.vironit.onlinepharmacy.model;

import java.time.Instant;
import java.util.Objects;

public abstract class Operation {

    private long id;
    private Instant date;
    private User owner;

    public Operation() {
    }

    public Operation(long id, Instant date, User owner) {
        this.id = id;
        this.date = date;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                date.equals(operation.date) &&
                owner.equals(operation.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, owner);
    }
}
