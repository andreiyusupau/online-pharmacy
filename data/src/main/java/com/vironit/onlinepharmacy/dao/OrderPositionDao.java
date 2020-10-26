package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OrderPosition;

public interface OrderPositionDao extends CrudDao<OrderPosition>, SlaveDao<OrderPosition> {
}
