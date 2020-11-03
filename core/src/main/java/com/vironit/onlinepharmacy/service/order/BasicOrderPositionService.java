package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.dao.OrderPositionDao;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicOrderPositionService implements OrderPositionService {

    private final OrderPositionDao orderPositionDao;

    public BasicOrderPositionService(OrderPositionDao orderPositionDao) {
        this.orderPositionDao = orderPositionDao;
    }

    @Override
    public boolean addAll(Collection<OrderPosition> orderPositions) {
        return orderPositionDao.addAll(orderPositions);
    }

    @Override
    public Collection<OrderPosition> getAllByOwnerId(long id) {
        return orderPositionDao.getAllByOwnerId(id);
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return orderPositionDao.removeAllByOwnerId(id);
    }

    @Override
    public void update(OrderPosition orderPosition) {
        orderPositionDao.update(orderPosition);
    }

    @Override
    public long add(OrderPosition orderPosition) {
        return orderPositionDao.add(orderPosition);
    }

    @Override
    public OrderPosition get(long id) {
        return orderPositionDao.get(id)
                .orElseThrow(() -> new OrderServiceException("Can't get order position. Order position with id " + id + " not found."));
    }

    @Override
    public Collection<OrderPosition> getAll() {
        return orderPositionDao.getAll();
    }

    @Override
    public void remove(long id) {
        orderPositionDao.remove(id);
    }
}
