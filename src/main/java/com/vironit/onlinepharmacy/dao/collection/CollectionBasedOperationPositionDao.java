package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.OperationPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedOperationPositionDao implements OperationPositionDao {

    private final IdGenerator idGenerator;
    private final Collection<OperationPosition> operationPositionList = new ArrayList<>();

    public CollectionBasedOperationPositionDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(OperationPosition operationPosition) {
        long id = idGenerator.getNextId();
        operationPosition.setId(id);
        boolean successfulAdd = operationPositionList.add(operationPosition);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<OperationPosition> get(long id) {
        return operationPositionList.stream()
                .filter(operationPosition -> operationPosition.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<OperationPosition> getAll() {
        return operationPositionList;
    }

    @Override
    public boolean update(OperationPosition updatedOperationPosition) {
        return remove(updatedOperationPosition.getId()) && operationPositionList.add(updatedOperationPosition);
    }

    @Override
    public boolean remove(long id) {
        return operationPositionList.removeIf(operationPosition -> operationPosition.getId() == id);
    }

    @Override
    public boolean addAll(Collection<OperationPosition> operationPositions) {
        operationPositions.forEach(this::add);
        return true;
    }

    @Override
    public Collection<OperationPosition> getAllByOwnerId(long id) {
        return operationPositionList.stream()
                .filter(operationPosition -> operationPosition.getOperation().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return operationPositionList.removeIf(operationPosition -> operationPosition.getOperation().getId() == id);
    }
}
