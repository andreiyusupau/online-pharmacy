package com.vironit.onlinepharmacy.model;

public class OperationPosition extends Position{

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
}
