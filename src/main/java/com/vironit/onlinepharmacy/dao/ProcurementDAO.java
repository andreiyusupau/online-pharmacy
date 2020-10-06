package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Procurement;

import java.util.Collection;

public interface ProcurementDAO extends DAO<Procurement>, SlaveDAO<Procurement>, MasterDAO<OperationPosition> {

    boolean createAll(Collection<OperationPosition> positions);
}
