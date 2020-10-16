package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Order;

public interface OrderDao extends Dao<Order>, SlaveDao<Order> {

}
