package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.StockPosition;
import org.springframework.stereotype.Component;

@Component
public class PositionDataToStockPositionConverter implements Converter<StockPosition, PositionData>{
    @Override
    public StockPosition convert(PositionData positionData) {
        StockPosition stockPosition=new StockPosition();
        stockPosition.setQuantity(positionData.getQuantity());
        return stockPosition;
    }
}