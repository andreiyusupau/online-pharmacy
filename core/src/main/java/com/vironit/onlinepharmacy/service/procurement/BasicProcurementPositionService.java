package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.ProcurementPositionDao;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProcurementPositionService implements ProcurementPositionService {

    private final ProcurementPositionDao procurementPositionDao;

    public BasicProcurementPositionService(ProcurementPositionDao procurementPositionDao) {
        this.procurementPositionDao = procurementPositionDao;
    }

    @Override
    public boolean addAll(Collection<ProcurementPosition> positionData) {
        return procurementPositionDao.addAll(positionData);
    }

    @Override
    public Collection<ProcurementPosition> getAllByOwnerId(long id) {
        return procurementPositionDao.getAllByOwnerId(id);
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return procurementPositionDao.removeAllByOwnerId(id);
    }

    @Override
    public void update(ProcurementPosition procurementPosition) {
procurementPositionDao.update(procurementPosition);
    }

    @Override
    public long add(ProcurementPosition procurementPosition) {
        return procurementPositionDao.add(procurementPosition);
    }

    @Override
    public ProcurementPosition get(long id) {
        return procurementPositionDao.get(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't get procurement position. Procurement position with id " + id + " not found."));
    }

    @Override
    public Collection<ProcurementPosition> getAll() {
        return procurementPositionDao.getAll();
    }

    @Override
    public void remove(long id) {
procurementPositionDao.remove(id);
    }
}
