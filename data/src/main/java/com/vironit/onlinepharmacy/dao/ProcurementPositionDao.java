package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.ProcurementPosition;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.ProcurementPositionRepository}
 */
@Deprecated
public interface ProcurementPositionDao extends CrudDao<ProcurementPosition>, SlaveDao<ProcurementPosition> {
}

