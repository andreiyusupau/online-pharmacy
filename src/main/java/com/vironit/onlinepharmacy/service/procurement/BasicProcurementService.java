package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.OperationPositionDao;
import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.dto.ProcurementCreateData;
import com.vironit.onlinepharmacy.dto.ProcurementUpdateData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.procurement.exception.ProcurementException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicProcurementService implements ProcurementService {

    private final ProcurementDao procurementDao;
    private final OperationPositionDao operationPositionDao;
    private final StockService stockService;
    private final ProductService productService;
    private final UserService userService;

    public BasicProcurementService(ProcurementDao procurementDao, OperationPositionDao operationPositionDao, StockService stockService, ProductService productService, UserService userService) {
        this.procurementDao = procurementDao;
        this.operationPositionDao = operationPositionDao;
        this.stockService = stockService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public long add(ProcurementCreateData procurementCreateData) {
        User owner = userService.get(procurementCreateData.getOwnerId());
        Procurement procurement = new Procurement(-1, Instant.now(), owner, ProcurementStatus.PREPARATION);
        List<OperationPosition> operationPositions = procurementCreateData.getOperationPositionDataList()
                .stream()
                .map(positionData -> new OperationPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), procurement))
                .collect(Collectors.toList());
        operationPositionDao.addAll(operationPositions);
        return procurementDao.add(procurement);
    }

    @Override
    public Procurement get(long id) {
        return procurementDao.get(id)
                .orElseThrow(() -> new ProcurementException("Can't get procurement. Procurement with id " + id + " not found."));
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementDao.getAll();
    }

    @Override
    public void update(ProcurementUpdateData procurementUpdateData) {
        Procurement procurement = get(procurementUpdateData.getId());
        List<OperationPosition> operationPositions = procurementUpdateData.getOperationPositionDataList()
                .stream()
                .map(positionData -> new OperationPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()), procurement))
                .collect(Collectors.toList());
        operationPositionDao.removeAllByOwnerId(procurementUpdateData.getOwnerId());
        operationPositionDao.addAll(operationPositions);
    }

    @Override
    public void remove(long id) {
        procurementDao.remove(id);
        operationPositionDao.removeAllByOwnerId(id);
    }

    @Override
    public void approveProcurement(long id) {
        Procurement procurement = get(id);
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementDao.update(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        Procurement procurement = get(id);
        Collection<Position> positions = operationPositionDao.getAllByOwnerId(id)
                .stream()
                .map(operationPosition -> new Position(operationPosition.getId(), operationPosition.getQuantity(), operationPosition.getProduct()))
                .collect(Collectors.toList());
        stockService.put(positions);
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementDao.update(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        Procurement procurement = get(id);
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementDao.update(procurement);
    }
}
