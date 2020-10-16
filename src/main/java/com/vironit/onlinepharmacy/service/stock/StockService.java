package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.service.CRUDService;

import java.util.Collection;

public interface StockService extends CRUDService<Position, Position, Position> {

    boolean addAll(Collection<Position> positions);

    boolean reserve(Collection<OperationPosition> operationPositions);

    boolean take(long orderId);

    boolean annul(long orderId);
}
