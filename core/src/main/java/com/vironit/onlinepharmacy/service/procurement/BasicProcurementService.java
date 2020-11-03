package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.ProcurementDao;
import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.dto.ProcurementData;
import com.vironit.onlinepharmacy.model.*;
import com.vironit.onlinepharmacy.service.exception.ProcurementServiceException;
import com.vironit.onlinepharmacy.service.product.ProductService;
import com.vironit.onlinepharmacy.service.stock.StockService;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.util.Converter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    public long add(ProcurementData procurementData) {
        User owner = new User();
        owner.setId(procurementData.getOwnerId());
        Procurement procurement = new Procurement(-1, Instant.now(), owner, ProcurementStatus.PREPARATION);
        long id=procurementDao.add(procurement);
        procurement.setId(id);
        List<ProcurementPosition> procurementPositions = procurementData.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product=new Product();
                    product.setId(positionData.getProductId());
                    return new ProcurementPosition(-1, positionData.getQuantity(), product, procurement);
                })
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
    public void update(ProcurementData procurementData) {
        User owner = new User();
        owner.setId(procurementData.getOwnerId());
        Procurement procurement = get(procurementData.getId());
        procurement.setOwner(owner);
        List<ProcurementPosition> procurementPositions = procurementData.getPositionDataList()
                .stream()
                .map(positionData -> {
                    Product product=new Product();
                    product.setId(positionData.getProductId());
                    return new ProcurementPosition(-1, positionData.getQuantity(), product, procurement);
                })
                .collect(Collectors.toList());
        procurementPositionService.removeAllByOwnerId(procurementData.getOwnerId());
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
