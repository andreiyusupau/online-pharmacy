package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OrderDAO;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.order.exception.OrderException;
import com.vironit.onlinepharmacy.service.stock.StockService;

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
        return orderDAO.add(new Order(-1, Instant.now(), user, OrderStatus.PREPARATION));
    }

    @Override
    public void payForOrder(long id) throws OrderException {
        Order order = orderDAO.get(id)
                .orElseThrow(() -> new OrderException("Can't pay for order. Order with id "+id+" not found."));
        order.setStatus(OrderStatus.PAID);
        orderDAO.update(order);
    }

    @Override
    public void confirmOrder(long id) throws OrderException {
        Order order = orderDAO.get(id)
                .orElseThrow(() -> new OrderException("Can't confirm order. Order with id "+id+" not found."));
        order.setStatus(OrderStatus.IN_PROGRESS);
        Collection<OperationPosition> positions = orderDAO.getAllSlaves(id);
        stockService.reserve(positions);
        orderDAO.update(order);
    }

    @Override
    public void completeOrder(long id) throws OrderException {
        Order order = orderDAO.get(id)
                .orElseThrow(() -> new OrderException("Can't complete order. Order with id "+id+" not found."));
        order.setStatus(OrderStatus.COMPLETE);
        stockService.take(id);
        orderDAO.update(order);
    }

    @Override
    public void cancelOrder(long id) throws OrderException {
        Order order = orderDAO.get(id)
                .orElseThrow(() -> new OrderException("Can't cancel order. Order with id "+id+" not found."));
        order.setStatus(OrderStatus.CANCELED);
        stockService.annul(id);
        orderDAO.update(order);
    }

    @Override
    public long add(Order order) {
        //TODO: implement
        return 0;
    }

    @Override
    public Order get(long id) {
        return orderDAO.get(id)
                .orElseThrow(() -> new OrderException("Can't cancel order. Order with id "+id+" not found."));
    }

    @Override
    public Collection<Order> getAll() {
        return orderDAO.getAll();
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
