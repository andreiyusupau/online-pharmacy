package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Order;

import java.util.Collection;

public interface ReserveDao extends Dao<Order>{
    @Deprecated
    boolean reserve(Collection<OperationPosition> positions);
    @Deprecated
    boolean annul(long orderId);
}
