package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;

import java.util.Collection;

public interface StockDAO extends DAO<Position>, SlaveDAO<OperationPosition> {

    boolean createAll(Collection<Position> positions);

    boolean reserve(Collection<OperationPosition> positions);

    boolean annul(long orderId);
}
