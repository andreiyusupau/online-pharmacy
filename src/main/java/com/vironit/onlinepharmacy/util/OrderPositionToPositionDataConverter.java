package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.OrderPosition;

public class OrderPositionToPositionDataConverter implements Converter<PositionData, OrderPosition> {
    @Override
    public PositionData convert(OrderPosition operationPosition) {
        return new PositionData(operationPosition.getProduct().getId(), operationPosition.getQuantity());
    }
}
