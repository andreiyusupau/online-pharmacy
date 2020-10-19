package com.vironit.onlinepharmacy.model;

import java.util.Objects;

public class OperationPosition extends Position {

    private Operation operation;

    public OperationPosition() {

    }

    public OperationPosition(long id, int quantity, Product product, Operation operation) {
        super(id, quantity, product);
        this.operation = operation;
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
        if (!(o instanceof OperationPosition)) return false;
        if (!super.equals(o)) return false;
        OperationPosition that = (OperationPosition) o;
        return operation.equals(that.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), operation);
    }
}
