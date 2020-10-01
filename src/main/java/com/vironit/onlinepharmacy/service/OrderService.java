package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.model.Operation;
import com.vironit.onlinepharmacy.model.OperationStatus;

import java.util.Collection;

public class OrderService {

   private final DAO<Operation> operationDAO;

    public OrderService(DAO<Operation> operationDAO) {
        this.operationDAO = operationDAO;
    }

    public void addOrder(Operation operation){
        operationDAO.add(operation);
    }

    public void payForOrder(long id){
        Operation operation=operationDAO.get(id);
        operation.setStatus(OperationStatus.PAID);
operationDAO.update(operation);
    }

    public void getOrdersByUserId(long id){

    }

    public Collection<Operation> getAllOrders(){
return operationDAO.getAll();
    }

    public void updateOrder(Operation operation){
operationDAO.update(operation);
    }

    public void confirmOrder(long id){
        Operation operation=operationDAO.get(id);
        operation.setStatus(OperationStatus.IN_PROGRESS);
        operationDAO.update();
    }

}
