package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.model.OperationPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedOperationPositionDao implements OperationPositionDao {

    private final IdGenerator idGenerator;
    private final Collection<OperationPosition> operationPositionList = new ArrayList<>();

    public CollectionBasedOperationPositionDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(OperationPosition operationPosition) {
       /* long id = 0;
        boolean positionShouldBeCreated = true;
        for (OperationPosition procurementPosition : operationPositionList) {
            if (operationPosition.getProduct().equals(procurementPosition.getProduct())) {
                procurementPosition.setQuantity(procurementPosition.getQuantity() + operationPosition.getQuantity());
                id = procurementPosition.getId();
                positionShouldBeCreated = false;
                break;
            }
        }
        if (positionShouldBeCreated) {
            id = idGenerator.getNextId();
            operationPosition.setId(id);
            operationPositionList.add(operationPosition);
        }
        return id;*/
        long id = idGenerator.getNextId();
        operationPosition.setId(id);
        operationPositionList.add(operationPosition);
        return id;
    }

    @Override
    public Optional<OperationPosition> get(long id) {
        for (OperationPosition operationPosition : operationPositionList) {
            if (operationPosition.getId() == id) {
                return Optional.of(operationPosition);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<OperationPosition> getAll() {
        return operationPositionList;
    }

    @Override
    public boolean update(OperationPosition operationPosition) {
        if (remove(operationPosition.getId())) {
            return operationPositionList.add(operationPosition);
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        return operationPositionList.removeIf(operationPosition -> operationPosition.getId() == id);
    }

    @Override
    public boolean addAll(Collection<OperationPosition> operationPositions) {
        for (OperationPosition operationPosition : operationPositions) {
            add(operationPosition);
        }
        return true;
    }

    @Override
    public Collection<OperationPosition> getAllByOwnerId(long id) {
        Collection<OperationPosition> operationPositions = new ArrayList<>();
        for (OperationPosition operationPosition : operationPositionList) {
            if (operationPosition.getOperation().getId() == id) {
                operationPositions.add(operationPosition);
            }
        }
        return operationPositions;
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return false;
    }
}
