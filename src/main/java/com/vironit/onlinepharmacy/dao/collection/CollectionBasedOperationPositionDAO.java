package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.OperationPositionDAO;
import com.vironit.onlinepharmacy.model.OperationPosition;

import java.util.Collection;
import java.util.Optional;

public class CollectionBasedOperationPositionDAO implements OperationPositionDAO {

    @Override
    public long add(OperationPosition operationPosition) {
        return 0;
    }

    @Override
    public Optional<OperationPosition> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<OperationPosition> getAll() {
        return null;
    }

    @Override
    public boolean update(OperationPosition operationPosition) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public boolean addAll(Collection<OperationPosition> t) {
        return false;
    }

    @Override
    public Collection<OperationPosition> getAllByOwnerId(long id) {
        return null;
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return false;
    }
}
