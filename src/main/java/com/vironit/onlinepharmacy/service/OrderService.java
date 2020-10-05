package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.User;

import java.util.Collection;

public interface OrderService extends CRUDService<Order> {

    long createOrder(User user);

    void payForOrder(long id);

    void confirmOrder(long id);

    void completeOrder(long id);

    void cancelOrder(long id);

    Collection<Order> getOrdersByUserId(long id);



}
