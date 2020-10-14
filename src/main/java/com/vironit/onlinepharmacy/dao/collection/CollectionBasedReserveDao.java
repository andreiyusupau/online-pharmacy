package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ReserveDao;
import com.vironit.onlinepharmacy.model.OperationPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedReserveDao implements ReserveDao {

    private final Collection<OperationPosition> reserved = new ArrayList<>();

    @Override
    public long add(OperationPosition operationPosition) {
        return reserved.add(operationPosition) ? operationPosition.getId() : -1;
    }

    @Override
    public Optional<OperationPosition> get(long id) {
        return reserved.stream()
                .filter(operationPosition -> operationPosition.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<OperationPosition> getAll() {
        return reserved;
    }

    @Override
    public boolean update(OperationPosition operationPosition) {
        return remove(operationPosition.getId()) && reserved.add(operationPosition);
    }

    @Override
    public boolean remove(long id) {
        return reserved.removeIf(operationPosition -> operationPosition.getId() == id);
    }

    @Override
    public boolean addAll(Collection<OperationPosition> operationPositions) {
        return reserved.addAll(operationPositions);
    }

    @Override
    public Collection<OperationPosition> getAllByOwnerId(long id) {
        return reserved.stream()
                .filter(operationPosition -> operationPosition.getOperation().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return reserved.removeIf(operationPosition -> operationPosition.getOperation().getId() == id);
    }
}
