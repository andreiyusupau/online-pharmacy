package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Procurement;

public interface ProcurementDAO extends DAO<Procurement>,SlaveDAO<Procurement>, MasterDAO<Position>  {
}
