package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OrderPosition;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.OrderPositionRepository}
 */
@Deprecated
public interface OrderPositionDao extends CrudDao<OrderPosition>, SlaveDao<OrderPosition> {
}
