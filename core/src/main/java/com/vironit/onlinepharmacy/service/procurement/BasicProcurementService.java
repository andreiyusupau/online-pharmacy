package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.dto.ProcurementDto;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.repository.ProcurementRepository;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasicProcurementService implements ProcurementService {

    private final ProcurementRepository procurementRepository;
    private final ProcurementPositionService procurementPositionService;
    private final StockService stockService;
    private final Converter<PositionDto, ProcurementPosition> procurementPositionToPositionDataConverter;

    public BasicProcurementService(ProcurementRepository procurementRepository, ProcurementPositionService procurementPositionService, StockService stockService, Converter<PositionDto, ProcurementPosition> procurementPositionToPositionDataConverter) {
        this.procurementRepository = procurementRepository;
        this.procurementPositionService = procurementPositionService;
        this.stockService = stockService;
        this.procurementPositionToPositionDataConverter = procurementPositionToPositionDataConverter;
    }

    @Override
    public long add(ProcurementDto procurementDto) {
        User owner = new User();
        owner.setId(procurementDto.getOwnerId());
        Procurement procurement = new Procurement(-1, Instant.now(), owner, ProcurementStatus.PREPARATION);
        long id = procurementRepository.save(procurement)
                .getId();
        procurement.setId(id);
        List<ProcurementPosition> procurementPositions = procurementDto.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product = new Product();
                    product.setId(positionData.getProductId());
                    return new ProcurementPosition(-1, positionData.getQuantity(), product, procurement);
                })
                .collect(Collectors.toList());
        procurementPositionService.addAll(procurementPositions);
        return id;
    }

    @Override
    public Procurement get(long id) {
        return procurementRepository.findById(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't get procurement. Procurement with id " + id + " not found."));
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementRepository.findAll();
    }

    @Override
    public void update(ProcurementDto procurementDto) {
        User owner = new User();
        owner.setId(procurementDto.getOwnerId());
        Procurement procurement = get(procurementDto.getId());
        procurement.setOwner(owner);
        List<ProcurementPosition> procurementPositions = procurementDto.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product = new Product();
                    product.setId(positionData.getProductId());
                    return new ProcurementPosition(-1, positionData.getQuantity(), product, procurement);
                })
                .collect(Collectors.toList());
        procurementPositionService.removeAllByOwnerId(procurementDto.getOwnerId());
        procurementPositionService.addAll(procurementPositions);
    }

    @Override
    public void remove(long id) {
        procurementRepository.deleteById(id);
        procurementPositionService.removeAllByOwnerId(id);
    }

    @Override
    public void approveProcurement(long id) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't approve procurement. Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementRepository.save(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't complete procurement. Procurement with id " + id + " not found."));
        Collection<PositionDto> positionData = procurementPositionService.getAllByOwnerId(id)
                .stream()
                .map(procurementPositionToPositionDataConverter::convert)
                .collect(Collectors.toList());
        stockService.addAll(positionData);
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementRepository.save(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        Procurement procurement = procurementRepository.findById(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't cancel procurement. Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementRepository.save(procurement);
    }
}
