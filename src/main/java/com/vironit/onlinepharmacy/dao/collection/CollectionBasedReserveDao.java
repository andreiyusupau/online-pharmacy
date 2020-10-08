package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ReserveDao;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedReserveDao implements ReserveDao {

    private final IdGenerator idGenerator;
    private final Collection<Order> reserved= new ArrayList<>();

    public CollectionBasedReserveDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public boolean reserve(Collection<OperationPosition> positions) {
        return false;
    }

    @Override
    public boolean annul(long orderId) {
        return false;
    }

    @Override
    public long add(Order order) {
        return reserved.add(order) ?order.getId():-1;
    }

    @Override
    public Optional<Order> get(long id) {
        for (Order order : reserved) {
            if (order.getId() == id) {
                return Optional.of(order);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Order> getAll() {
        return reserved;
    }

    @Override
    public boolean update(Order order) {
        if(remove(order.getId())){
            return reserved.add(order);
        }else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        return reserved.removeIf(order -> order.getId() == id);
    }
}
