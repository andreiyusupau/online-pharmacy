package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OrderPositionDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.OrderPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.dao.jpa.JpaOrderPositionDao}
 */
@Deprecated
public class CollectionBasedOrderPositionDao implements OrderPositionDao {

    private final IdGenerator idGenerator;
    private final Collection<OrderPosition> orderPositionCollection = new ArrayList<>();

    public CollectionBasedOrderPositionDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(OrderPosition orderPosition) {
        long id = idGenerator.getNextId();
        orderPosition.setId(id);
        boolean successfulAdd = orderPositionCollection.add(orderPosition);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<OrderPosition> get(long id) {
        return orderPositionCollection.stream()
                .filter(orderPosition -> orderPosition.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<OrderPosition> getAll() {
        return orderPositionCollection;
    }

    @Override
    public boolean update(OrderPosition updatedOrderPosition) {
        return remove(updatedOrderPosition.getId()) && orderPositionCollection.add(updatedOrderPosition);
    }

    @Override
    public boolean remove(long id) {
        return orderPositionCollection.removeIf(orderPosition -> orderPosition.getId() == id);
    }

    @Override
    public boolean addAll(Collection<OrderPosition> orderPositions) {
        orderPositions.forEach(this::add);
        return true;
    }

    @Override
    public Collection<OrderPosition> getAllByOwnerId(long id) {
        return orderPositionCollection.stream()
                .filter(orderPosition -> orderPosition.getOrder()
                        .getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return orderPositionCollection.removeIf(orderPosition -> orderPosition.getOrder()
                .getId() == id);
    }
}
