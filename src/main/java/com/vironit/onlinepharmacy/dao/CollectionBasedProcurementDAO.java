package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Procurement;

import java.util.Collection;
import java.util.Optional;

public class CollectionBasedProcurementDAO implements ProcurementDAO {
    @Override
    public long add(Procurement procurement) {
        return 0;
    }

    @Override
    public Optional<Procurement> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Procurement> getAll() {
        return null;
    }

    @Override
    public boolean update(Procurement procurement) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public Collection<Procurement> getAllByOwnerId(long id) {
        return null;
    }
}
