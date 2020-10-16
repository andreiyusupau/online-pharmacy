package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.OrderUpdateData;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.service.CRUDService;
import com.vironit.onlinepharmacy.service.order.exception.OrderException;

import java.util.Collection;

public interface OrderService extends CRUDService<OrderCreateData, Order, OrderUpdateData> {

    void payForOrder(long id) throws OrderException;

    void confirmOrder(long id) throws OrderException;

    void completeOrder(long id) throws OrderException;

    void cancelOrder(long id) throws OrderException;

    Collection<Order> getOrdersByUserId(long id);


}
