package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.StockPosition;
import com.vironit.onlinepharmacy.service.CrudService;

import java.util.Collection;

public interface StockService extends CrudService<PositionData, StockPosition, StockPosition> {

    boolean addAll(Collection<PositionData> positionData);

    boolean reserveInStock(Collection<OrderPosition> positions);

    boolean takeFromStock(Collection<OrderPosition> positions);

    boolean annulReservationInStock(Collection<OrderPosition> positions);
}
