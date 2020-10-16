package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Procurement;

public interface ProcurementDao extends Dao<Procurement>, SlaveDao<Procurement>, PaginationDao<Procurement> {

}
