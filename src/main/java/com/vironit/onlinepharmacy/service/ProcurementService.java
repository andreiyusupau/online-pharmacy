package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.dao.DAO;
import com.vironit.onlinepharmacy.model.Operation;

public class ProcurementService {

    private final DAO<Operation> operationDAO;

    public ProcurementService(DAO<Operation> operationDAO) {
        this.operationDAO = operationDAO;
    }

    public void createProcurement(){

    }

    public void updateProcurement(){

    }
}
