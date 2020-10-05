package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.OrderDAO;
import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.OrderStatus;
import com.vironit.onlinepharmacy.model.User;

import java.time.Instant;
import java.util.Collection;

public class BasicOrderService implements OrderService {

    private final OrderDAO orderDAO;
    private final StockService stockService;

    public BasicOrderService(OrderDAO orderDAO, StockService stockService) {
        this.orderDAO = orderDAO;
        this.stockService = stockService;
    }

    @Override
    public long createOrder(User user) {
        return orderDAO.add(new Order(-1, Instant.now(),user, OrderStatus.PREPARATION));
    }

    @Override
    public void payForOrder(long id) {
        //TODO:add exception
        Order order=orderDAO.get(id).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        orderDAO.update(order);
    }

    @Override
    public void confirmOrder(long id) {
        //TODO:add exception
        Order order=orderDAO.get(id).orElseThrow();
        order.setStatus(OrderStatus.PAID);
        orderDAO.update(order);
    }

    @Override
    public void completeOrder(long id) {
        //TODO:add exception
        Order order=orderDAO.get(id).orElseThrow();
        order.setStatus(OrderStatus.COMPLETE);
        orderDAO.update(order);
    }

    @Override
    public void cancelOrder(long id) {
        //TODO:add exception
        Order order=orderDAO.get(id).orElseThrow();
        order.setStatus(OrderStatus.CANCELED);
        orderDAO.update(order);
    }

    @Override
    public long add(Order order) {
        return 0;
    }

    @Override
    public Order get(long id) {
        //TODO:add exception
        return orderDAO.get(id).orElseThrow();
    }

    @Override
    public Collection<Order> getAll() {
        return null;
    }

    @Override
    public Collection<Order> getOrdersByUserId(long id) {
        return orderDAO.getAllByOwnerId(id);
    }

    @Override
    public void update(Order order) {

    }

    @Override
    public void remove(long id) {

    }
}
