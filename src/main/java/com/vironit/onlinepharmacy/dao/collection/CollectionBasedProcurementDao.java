package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.model.Procurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedProcurementDao implements ProcurementDao {

    private final IdGenerator idGenerator;
    private final Collection<Procurement> procurementList=new ArrayList<>();

    public CollectionBasedProcurementDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Procurement procurement) {
        long id= idGenerator.getNextId();
        procurement.setId(id);
        procurementList.add(procurement);
        return procurement.getId();
    }

    @Override
    public Optional<Procurement> get(long id) {
       return procurementList.stream()
               .filter(procurement -> procurement.getId()==id)
               .findFirst();
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementList;
    }

    @Override
    public boolean update(Procurement updatedProcurement) {
        if(remove(updatedProcurement.getId())){
            return procurementList.add(updatedProcurement);
        }else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        return procurementList.removeIf(procurement -> procurement.getId()==id);
    }

    @Override
    public boolean addAll(Collection<Procurement> procurements) {
        for (Procurement procurement:procurements){
            add(procurement);
        }
        return true;
    }

    @Override
    public Collection<Procurement> getAllByOwnerId(long id) {
        return procurementList.stream()
                .filter(procurement -> procurement.getOwner().getId()==id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return procurementList.removeIf(procurement -> procurement.getOwner().getId()==id);
    }
}
