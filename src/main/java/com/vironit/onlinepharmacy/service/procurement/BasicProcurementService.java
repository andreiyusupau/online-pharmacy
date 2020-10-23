package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.dto.ProcurementCreateData;
import com.vironit.onlinepharmacy.dto.ProcurementUpdateData;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.util.Converter;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicProcurementService implements ProcurementService {

    private final ProcurementDao procurementDao;
    private final ProcurementPositionService procurementPositionService;
    private final StockService stockService;
    private final ProductService productService;
    private final UserService userService;
    private final Converter<PositionData, ProcurementPosition> procurementPositionToPositionDataConverter;

    public BasicProcurementService(ProcurementDao procurementDao, ProcurementPositionService procurementPositionService, StockService stockService, ProductService productService, UserService userService, Converter<PositionData, ProcurementPosition> procurementPositionToPositionDataConverter) {
        this.procurementDao = procurementDao;
        this.procurementPositionService = procurementPositionService;
        this.stockService = stockService;
        this.productService = productService;
        this.userService = userService;
        this.procurementPositionToPositionDataConverter = procurementPositionToPositionDataConverter;
    }

    @Override
    public long add(ProcurementCreateData procurementCreateData) {
        User owner = userService.get(procurementCreateData.getOwnerId());
        Procurement procurement = new Procurement(-1, Instant.now(), owner, ProcurementStatus.PREPARATION);
        long id=procurementDao.add(procurement);
        procurement.setId(id);
        List<ProcurementPosition> procurementPositions = procurementCreateData.getPositionDataList()
                .stream()
                .map(positionData -> new ProcurementPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), procurement))
                .collect(Collectors.toList());
        procurementPositionService.addAll(procurementPositions);
        return id;
    }

    @Override
    public Procurement get(long id) {
        return procurementDao.get(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't get procurement. Procurement with id " + id + " not found."));
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementDao.getAll();
    }

    @Override
    public void update(ProcurementUpdateData procurementUpdateData) {
        User owner = userService.get(procurementUpdateData.getOwnerId());
        Procurement procurement = get(procurementUpdateData.getId());
        procurement.setOwner(owner);
        List<ProcurementPosition> procurementPositions = procurementUpdateData.getPositionDataList()
                .stream()
                .map(positionData -> new ProcurementPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), procurement))
                .collect(Collectors.toList());
        procurementPositionService.removeAllByOwnerId(procurementUpdateData.getOwnerId());
        procurementPositionService.addAll(procurementPositions);
    }

    @Override
    public void remove(long id) {
        procurementDao.remove(id);
        procurementPositionService.removeAllByOwnerId(id);
    }

    @Override
    public void approveProcurement(long id) {
        Procurement procurement = procurementDao.get(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't approve procurement. Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementDao.update(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        Procurement procurement = procurementDao.get(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't complete procurement. Procurement with id " + id + " not found."));
        Collection<PositionData> positionData = procurementPositionService.getAllByOwnerId(id)
                .stream()
                .map(procurementPositionToPositionDataConverter::convert)
                .collect(Collectors.toList());
        stockService.addAll(positionData);
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementDao.update(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        Procurement procurement = procurementDao.get(id)
                .orElseThrow(() -> new ProcurementServiceException("Can't cancel procurement. Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementDao.update(procurement);
    }
}
