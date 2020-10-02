package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Operation;

public interface OrderService extends CRUDService<Operation> {

    void payForOrder(long id);

    void getOrdersByUserId(long id);

    void confirmOrder(long id);

}
