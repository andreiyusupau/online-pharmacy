package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.dao.ProcurementDAO;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.ProcurementStatus;
import com.vironit.onlinepharmacy.model.User;

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
        //TODO:add exception
        return procurementDAO.get(id).orElseThrow();
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
        //TODO:add exception
        Procurement procurement = procurementDAO.get(id).orElseThrow();
        procurement.setProcurementStatus(ProcurementStatus.APPROVED);
        procurementDAO.update(procurement);
    }

    @Override
    public void completeProcurement(long id) {
        //TODO:add exception
        Procurement procurement = procurementDAO.get(id).orElseThrow();
        stockService.put();
        procurement.setProcurementStatus(ProcurementStatus.COMPLETE);
        procurementDAO.update(procurement);
    }

    @Override
    public void cancelProcurement(long id) {
        //TODO:add exception
        Procurement procurement = procurementDAO.get(id).orElseThrow();
        procurement.setProcurementStatus(ProcurementStatus.CANCELED);
        procurementDAO.update(procurement);
    }
}
