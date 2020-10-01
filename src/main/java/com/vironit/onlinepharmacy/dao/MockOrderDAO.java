package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MockOrderDAO implements DAO<Operation> {

    private final List<Operation> operationList= new ArrayList<>();
private long currentId=0;

    @Override
    public long add(Operation operation) {
        operation.setId(currentId);
        currentId++;
        operationList.add(operation);
        return operation.getId();
    }

    @Override
    public Operation get(long id) {
        for(Operation operation:operationList){
            if(operation.getId()==id){
                return operation;
            }
        }
        //TODO:replace
        return null;
    }

    @Override
    public Collection<Operation> getAll() {
        return operationList;
    }

    @Override
    public boolean update(Operation operation) {
        for(Operation currentOperation:operationList){
            if(currentOperation.getId()==operation.getId()){
                currentOperation=operation;
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean remove(long id) {
        return operationList.removeIf(currentOperation -> currentOperation.getId() == id);
    }
}
