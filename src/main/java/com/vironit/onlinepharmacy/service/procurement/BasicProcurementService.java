package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.OperationPositionDAO;
import com.vironit.onlinepharmacy.dao.ProcurementDAO;
import com.vironit.onlinepharmacy.dto.ProcurementData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.service.procurement.exception.ProcurementException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicProcurementService implements ProcurementService {

    private final ProcurementDAO procurementDAO;
    private final OperationPositionDAO operationPositionDAO;
    private final StockService stockService;
    private final ProductService productService;
    private final UserService userService;

    public BasicProcurementService(ProcurementDAO procurementDAO, OperationPositionDAO operationPositionDAO, StockService stockService, ProductService productService, UserService userService) {
        this.procurementDAO = procurementDAO;
        this.operationPositionDAO = operationPositionDAO;
        this.stockService = stockService;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public long add(ProcurementData procurementData) {
        User owner= userService.get(procurementData.getOwnerId());
        Procurement procurement=new Procurement(-1,Instant.now(),owner,ProcurementStatus.PREPARATION);
        List<OperationPosition> operationPositions=procurementData.getProcurementPositionDataList()
                .stream()
                .map(positionData-> new OperationPosition(-1, positionData.getQuantity(), productService.get(positionData.getProductId()),procurement))
                .collect(Collectors.toList());
        operationPositionDAO.addAll(operationPositions);
        return procurementDAO.add(procurement);
    }

    @Override
    public long add(Procurement procurement) {
        return 0;//TODO:remove
    }

    @Override
    public Procurement get(long id) {
        return procurementDAO.get(id)
                .orElseThrow(() -> new ProcurementException("Can't get procurement. Procurement with id " + id + " not found."));
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementDAO.getAll();
    }

    @Override
    public void update(Procurement procurement) {
        //TODO:add input dto
        procurementDAO.update(procurement);
    }

    @Override
    public void remove(long id) {
        procurementDAO.remove(id);
        operationPositionDAO.removeAllByOwnerId(id);
    }

    @Override
    public void approveProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(() -> new ProcurementException("Can't approve procurement.Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementDAO.update(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(() -> new ProcurementException("Can't complete procurement.Procurement with id " + id + " not found."));
        Collection<Position> positions = procurementDAO.getAllSlaves(id)
                .stream()
                .map(operationPosition -> new Position(operationPosition.getId(),operationPosition.getQuantity(), operationPosition.getProduct()))
                .collect(Collectors.toList());
        stockService.put(positions);
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementDAO.update(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(() -> new ProcurementException("Can't cancel procurement.Procurement with id " + id + " not found."));
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementDAO.update(procurement);
    }
}
