package com.vironit.onlinepharmacy;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.dao.MockOrderDAO;
import com.vironit.onlinepharmacy.model.Operation;
import com.vironit.onlinepharmacy.service.OrderService;

public class Main {
    public static void main(String[] args) {
        DAO<Operation> orderDAO = new MockOrderDAO();
        OrderService orderService= new OrderService(orderDAO);

    }
}
