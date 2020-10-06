package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Order;

public interface OrderDAO extends DAO<Order>, SlaveDAO<Order>, MasterDAO<OperationPosition> {

}
