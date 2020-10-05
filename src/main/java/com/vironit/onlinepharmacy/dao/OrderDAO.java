package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.Position;

public interface OrderDAO extends DAO<Order>,SlaveDAO<Order>, MasterDAO<Position> {

}
