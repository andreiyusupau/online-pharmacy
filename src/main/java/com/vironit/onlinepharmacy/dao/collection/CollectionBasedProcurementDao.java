package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.model.Procurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedProcurementDao implements ProcurementDao {

    private final IdGenerator idGenerator;
    private final Collection<Procurement> procurementList = new ArrayList<>();

    public CollectionBasedProcurementDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Procurement procurement) {
        long id = idGenerator.getNextId();
        procurement.setId(id);
        boolean successfulAdd = procurementList.add(procurement);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<Procurement> get(long id) {
        return procurementList.stream()
                .filter(procurement -> procurement.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementList;
    }

    @Override
    public boolean update(Procurement updatedProcurement) {
        return remove(updatedProcurement.getId()) && procurementList.add(updatedProcurement);
    }

    @Override
    public boolean remove(long id) {
        return procurementList.removeIf(procurement -> procurement.getId() == id);
    }

    @Override
    public boolean addAll(Collection<Procurement> procurements) {
        procurements.forEach(this::add);
        return true;
    }

    @Override
    public Collection<Procurement> getAllByOwnerId(long id) {
        return procurementList.stream()
                .filter(procurement -> procurement.getOwner().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return procurementList.removeIf(procurement -> procurement.getOwner().getId() == id);
    }

    @Override
    public int getTotalElements() {
        return procurementList.size();
    }

    @Override
    public Collection<Procurement> getPage(int currentPage, int pageLimit) {
        return procurementList.stream()
                .skip((currentPage-1)*pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }
}
