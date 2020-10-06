package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dao.ProcurementDAO;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.procurement.exception.ProcurementException;
import com.vironit.onlinepharmacy.service.stock.StockService;

import java.time.Instant;
import java.util.Collection;

public class BasicProcurementService implements ProcurementService {

    private final ProcurementDAO procurementDAO;
private final StockService stockService;
    public BasicProcurementService(ProcurementDAO procurementDAO, StockService stockService) {
        this.procurementDAO = procurementDAO;
        this.stockService = stockService;
    }

    @Override
    public long add(Procurement procurement) {
        return procurementDAO.add(procurement);
    }

    @Override
    public Procurement get(long id) {
        return procurementDAO.get(id)
                .orElseThrow(()->new ProcurementException("Can't get procurement. Procurement with id "+id+" not found."));
    }

    @Override
    public Collection<Procurement> getAll() {
        return procurementDAO.getAll();
    }

    @Override
    public void update(Procurement procurement) {
        procurementDAO.update(procurement);
    }

    @Override
    public void remove(long id) {
        procurementDAO.remove(id);
    }

    @Override
    public long createProcurement(User user) {
        return procurementDAO.add(new Procurement(-1, Instant.now(), user, ProcurementStatus.PREPARATION));
    }

    @Override
    public void approveProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(()->new ProcurementException("Can't approve procurement.Procurement with id "+id+" not found."));
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementDAO.update(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(()->new ProcurementException("Can't complete procurement.Procurement with id "+id+" not found."));
        Collection<Position> positions=procurementDAO.getAllSlaves(id);
        stockService.put(positions);
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementDAO.update(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        Procurement procurement = procurementDAO.get(id)
                .orElseThrow(()->new ProcurementException("Can't cancel procurement.Procurement with id "+id+" not found."));
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementDAO.update(procurement);
    }
}
