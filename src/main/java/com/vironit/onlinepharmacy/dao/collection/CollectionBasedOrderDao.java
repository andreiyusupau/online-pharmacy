package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedOrderDao implements OrderDao {

    private final IdGenerator idGenerator;
    private final Collection<Order> orderList = new ArrayList<>();

    public CollectionBasedOrderDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Order order) {
        long id=idGenerator.getNextId();
        order.setId(id);
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
        if(remove(order.getId())){
            return orderList.add(order);
        }else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        return orderList.removeIf(order -> order.getId() == id);
    }

    @Override
    public boolean addAll(Collection<Order> orders) {
        for (Order order:orders){
            add(order);
        }
        return true;
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
        return orderList.removeIf(order -> order.getOwner().getId()==id);
    }

}
