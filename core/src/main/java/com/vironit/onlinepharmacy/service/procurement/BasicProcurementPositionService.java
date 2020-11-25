package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.repository.ProcurementPositionRepository;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProcurementPositionService implements ProcurementPositionService {

    private final ProcurementPositionRepository procurementPositionRepository;

    public BasicProcurementPositionService(ProcurementPositionRepository procurementPositionRepository) {
        this.procurementPositionRepository = procurementPositionRepository;
    }

    @Override
    public boolean addAll(Collection<ProcurementPosition> positionData) {
        return procurementPositionRepository.saveAll(positionData).size() == positionData.size();
    }

    @Override
    public Collection<ProcurementPosition> getAllByOwnerId(long id) {
        return procurementPositionRepository.getAllByOwnerId(id);
    }

    @Override
    public boolean removeAllByOwnerId(long id) {
        return procurementPositionRepository.removeAllByOwnerId(id);
    }

    @Override
    public void update(ProcurementPosition procurementPosition) {
        procurementPositionRepository.save(procurementPosition);
    }

    @Override
    public long add(ProcurementPosition procurementPosition) {
        return procurementPositionRepository.save(procurementPosition)
                .getId();
    }

    @Override
    public ProcurementPosition get(long id) {
        return procurementPositionRepository.findById(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't get procurement position. Procurement position with id " + id + " not found."));
    }

    @Override
    public Collection<ProcurementPosition> getAll() {
        return procurementPositionRepository.findAll();
    }

    @Override
    public void remove(long id) {
        procurementPositionRepository.deleteById(id);
    }
}
