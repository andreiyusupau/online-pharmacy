package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dto.OrderData;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.service.CrudService;

import java.util.Collection;

public interface OrderService extends CrudService<OrderData, Order> {

    void payForOrder(long id);

    void confirmOrder(long id);

    void completeOrder(long id);

    void cancelOrder(long id);

    Collection<Order> getAllByOwnerId(long id);

}
