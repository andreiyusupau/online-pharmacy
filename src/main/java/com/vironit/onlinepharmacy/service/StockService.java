package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.model.Procurement;

public interface StockService extends CRUDService<Position> {

    void put(Procurement procurement);

    void take(Position position);
}
