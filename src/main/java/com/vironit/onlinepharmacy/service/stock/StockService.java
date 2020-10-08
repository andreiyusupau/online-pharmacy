package com.vironit.onlinepharmacy.service.stock;

import com.vironit.onlinepharmacy.model.Order;
import com.vironit.onlinepharmacy.model.Position;
import com.vironit.onlinepharmacy.service.CRUDService;

import java.util.Collection;

public interface StockService extends CRUDService<Position,Position,Position> {

    boolean put(Collection<Position> positions);

    boolean reserve(Order order);

    boolean take(long orderId);

    boolean annul(long orderId);
}
