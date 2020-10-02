package com.vironit.onlinepharmacy.model;

import java.time.Instant;
import java.util.Objects;

public class Operation {

    private long id;
    private Instant date;
    private OperationType type;
    private OperationStatus status;
    private User owner;

    public Operation() {
    }

    public Operation(long id, Instant date, OperationType type, OperationStatus status, User owner) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.status = status;
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

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
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
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return id == operation.id &&
                date.equals(operation.date) &&
                type == operation.type &&
                status == operation.status &&
                owner.equals(operation.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, type, status, owner);
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}
