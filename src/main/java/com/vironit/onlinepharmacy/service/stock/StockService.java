package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.service.CRUDService;

import java.util.Collection;

public interface StockService extends CRUDService<Position> {

    boolean put(Collection<Position> positions);

    boolean reserve(Collection<OperationPosition> positions);

    boolean take(long orderId);

    boolean annul(long orderId);
}
