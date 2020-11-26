package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Procurement;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.ProcurementRepository}
 */
@Deprecated
public interface ProcurementDao extends CrudDao<Procurement>, SlaveDao<Procurement>, PaginationDao<Procurement> {

}
