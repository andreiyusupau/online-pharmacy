package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.StockPosition;
import com.vironit.onlinepharmacy.service.CrudService;

import java.util.Collection;

public interface StockService extends CrudService<PositionData, StockPosition, StockPosition> {

    boolean addAll(Collection<PositionData> positionData);

    boolean reserveInStock(Collection<OperationPosition> operationPositions);

    boolean takeFromStock(Collection<OperationPosition> operationPositions);

    boolean annulReservationInStock(Collection<OperationPosition> operationPositions);
}
