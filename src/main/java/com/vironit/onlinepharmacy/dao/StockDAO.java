package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Position;

import java.util.Collection;

public interface StockDAO extends DAO<Position>,SlaveDAO<Position> {

    boolean createAll(Collection<Position> positions);

    boolean reserve(Collection<Position> positions);

    boolean annul(long orderId);
}
