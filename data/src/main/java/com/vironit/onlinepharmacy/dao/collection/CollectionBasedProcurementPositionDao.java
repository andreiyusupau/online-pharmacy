package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import com.vironit.onlinepharmacy.model.ProcurementPosition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.dao.jpa.JpaProcurementPositionDao}
 */
@Deprecated
public class CollectionBasedProcurementPositionDao implements ProcurementPositionDao {


    private final IdGenerator idGenerator;
    private final Collection<ProcurementPosition> procurementPositionCollection = new ArrayList<ProcurementPosition>();

    public CollectionBasedProcurementPositionDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(ProcurementPosition procurementPosition) {
        long id = idGenerator.getNextId();
        procurementPosition.setId(id);
        boolean successfulAdd = procurementPositionCollection.add(procurementPosition);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<ProcurementPosition> get(long id) {
        return procurementPositionCollection.stream()
                .filter(procurementPosition -> procurementPosition.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<ProcurementPosition> getAll() {
        return procurementPositionCollection;
    }

    @Override
    public boolean update(ProcurementPosition updatedProcurementPosition) {
        return remove(updatedProcurementPosition.getId()) && procurementPositionCollection.add(updatedProcurementPosition);
    }

    @Override
    public boolean remove(long id) {
        return procurementPositionCollection.removeIf(procurementPosition -> procurementPosition.getId() == id);
    }

    @Override
    public boolean addAll(Collection<ProcurementPosition> procurementPositions) {
        procurementPositions.forEach(this::add);
        return true;
    }

    @Override
    public Collection<ProcurementPosition> getAllByOwnerId(long id) {
        return procurementPositionCollection.stream()
                .filter(procurementPosition -> procurementPosition.getProcurement()
                        .getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return procurementPositionCollection.removeIf(procurementPosition -> procurementPosition.getProcurement()
                .getId() == id);
    }
}
