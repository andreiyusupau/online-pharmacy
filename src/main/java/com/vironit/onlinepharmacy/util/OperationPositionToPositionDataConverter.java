package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.OperationPosition;

public class OperationPositionToPositionDataConverter implements Converter<PositionData, OperationPosition> {
    @Override
    public PositionData convert(OperationPosition operationPosition) {
        return new PositionData(operationPosition.getProduct().getId(), operationPosition.getQuantity());
    }
}
