package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.model.OrderPosition;
import com.vironit.onlinepharmacy.model.StockPosition;
import com.vironit.onlinepharmacy.service.CrudService;

import java.util.Collection;

public interface StockService extends CrudService<PositionDto, StockPosition> {

    boolean addAll(Collection<PositionDto> positionData);

    boolean reserveInStock(Collection<OrderPosition> positions);

    boolean takeFromStock(Collection<OrderPosition> positions);

    boolean annulReservationInStock(Collection<OrderPosition> positions);
}
