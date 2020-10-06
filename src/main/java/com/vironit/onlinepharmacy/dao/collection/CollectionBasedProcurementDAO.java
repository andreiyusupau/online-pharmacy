package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProcurementDAO;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Procurement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedProcurementDAO implements ProcurementDAO {

    private final Collection<Procurement> procurementList=new ArrayList<>();
    private final Collection<OperationPosition> positionList= new ArrayList<>();
    private long currentId = 0;

    @Override
    public long add(Procurement procurement) {
        procurement.setId(currentId);
        currentId++;
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
        for (Procurement procurement : procurementList) {
            if (procurement.getId() == updatedProcurement.getId()) {
                procurement = updatedProcurement;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(long id) {
        return procurementList.removeIf(procurement -> procurement.getId()==id);
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

    @Override
    public Collection<OperationPosition> getAllSlaves(long masterId) {
        return positionList.stream().
                filter(operationPosition -> operationPosition.getOperation().getId()==masterId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean createAll(Collection<OperationPosition> positions) {
        for (OperationPosition createdPosition : positions) {
            boolean positionShouldBeCreated = true;
            for (Position procurementPosition : positionList) {
                if (createdPosition.getProduct().equals(procurementPosition.getProduct())) {
                    procurementPosition.setQuantity(procurementPosition.getQuantity() + createdPosition.getQuantity());
                    positionShouldBeCreated = false;
                    break;
                }
            }
            if (positionShouldBeCreated) {
                positionList.add(createdPosition);
            }
        }
        return true;
    }
}
