package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OrderDao;
import com.vironit.onlinepharmacy.model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedOrderDao implements OrderDao {

    private final IdGenerator idGenerator;
    private final Collection<Order> orderList = new ArrayList<>();

    public CollectionBasedOrderDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Order order) {
        long id = idGenerator.getNextId();
        order.setId(id);
        boolean successfulAdd = orderList.add(order);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<Order> get(long id) {
        return orderList.stream()
                .filter(order -> order.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<Order> getAll() {
        return orderList;
    }

    @Override
    public boolean update(Order order) {
        return remove(order.getId()) && orderList.add(order);
    }

    @Override
    public boolean remove(long id) {
        return orderList.removeIf(order -> order.getId() == id);
    }

    @Override
    public boolean addAll(Collection<Order> orders) {
        orders.forEach(this::add);
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
        return orderList.removeIf(order -> order.getOwner().getId() == id);
    }

    @Override
    public int getTotalElements() {
        return orderList.size();
    }

    @Override
    public Collection<Order> getPage(int currentPage, int pageLimit) {
        return orderList.stream()
                .skip((currentPage-1)*pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }
}
