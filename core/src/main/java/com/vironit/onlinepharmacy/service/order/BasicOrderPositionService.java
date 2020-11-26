package com.vironit.onlinepharmacy.service.order;

import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.repository.OrderPositionRepository;
import com.vironit.onlinepharmacy.service.exception.OrderServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicOrderPositionService implements OrderPositionService {

    private final OrderPositionRepository orderPositionRepository;

    public BasicOrderPositionService(OrderPositionRepository orderPositionRepository) {
        this.orderPositionRepository = orderPositionRepository;
    }

    @Override
    public boolean addAll(Collection<OrderPosition> orderPositions) {
        return orderPositionRepository.saveAll(orderPositions).size() == orderPositions.size();
    }

    @Override
    public Collection<OrderPosition> getAllByOwnerId(long id) {
        return orderPositionRepository.findByOrder_Id(id);
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return orderPositionRepository.deleteByOrder_Id(id);
    }

    @Override
    public void update(OrderPosition orderPosition) {
        orderPositionRepository.save(orderPosition);
    }

    @Override
    public long add(OrderPosition orderPosition) {
        return orderPositionRepository.save(orderPosition)
                .getId();
    }

    @Override
    public OrderPosition get(long id) {
        return orderPositionRepository.findById(id)
                .orElseThrow(() -> new OrderServiceException("Can't get order position. Order position with id " + id + " not found."));
    }

    @Override
    public Collection<OrderPosition> getAll() {
        return orderPositionRepository.findAll();
    }

    @Override
    public void remove(long id) {
        orderPositionRepository.deleteById(id);
    }
}
