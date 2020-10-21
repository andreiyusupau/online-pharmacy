package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dto.OrderCreateData;
import com.vironit.onlinepharmacy.dto.OrderUpdateData;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.service.CrudService;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;

import java.util.Collection;

public interface OrderService extends CrudService<OrderCreateData, Order, OrderUpdateData> {

    void payForOrder(long id) throws OrderServiceException;

    void confirmOrder(long id) throws OrderServiceException;

    void completeOrder(long id) throws OrderServiceException;

    void cancelOrder(long id) throws OrderServiceException;

    Collection<Order> getOrdersByUserId(long id);


}
