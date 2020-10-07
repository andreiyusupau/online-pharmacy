package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OrderDAO;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedOrderDAO implements OrderDAO {

    private final Collection<Order> orderList = new ArrayList<>();
    private long currentId = 0;

    @Override
    public long add(Order order) {
        order.setId(currentId);
        currentId++;
        orderList.add(order);
        return order.getId();
    }

    @Override
    public Optional<Order> get(long id) {
        for (Order order : orderList) {
            if (order.getId() == id) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Order> getAll() {
        return orderList;
    }

    @Override
    public boolean update(Order order) {
        for (Order currentOrder : orderList) {
            if (currentOrder.getId() == order.getId()) {
                currentOrder = order;
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean remove(long id) {
        return orderList.removeIf(currentOperation -> currentOperation.getId() == id);
    }

    @Override
    public boolean addAll(Collection<Order> t) {
        return false;
    }

    @Override
    public Collection<Order> getAllByOwnerId(long id) {
        Collection<Order> orders = new ArrayList<>();
        for (Order currentOrder : orderList) {
            if (currentOrder.getOwner().getId() == id) {
                orders.add(currentOrder);
            }
        }
        return orders;
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return false;
    }

    @Override
    public Collection<OperationPosition> getAllSlaves(long masterId) {
        return null;
    }
}
