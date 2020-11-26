package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Order;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.OrderRepository}
 */
@Deprecated
public interface OrderDao extends CrudDao<Order>, SlaveDao<Order>, PaginationDao<Order> {

}
