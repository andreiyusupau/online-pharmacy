package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Position;

public interface StockDAO extends DAO<Position>,SlaveDAO<Position> {
}
