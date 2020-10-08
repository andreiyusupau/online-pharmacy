package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;

import java.util.Collection;

public interface StockDao extends Dao<Position>, SlaveDao<OperationPosition> {

    boolean createAll(Collection<Position> positions);
@Deprecated
    boolean reserve(Collection<OperationPosition> positions);
@Deprecated
    boolean annul(long orderId);
}
