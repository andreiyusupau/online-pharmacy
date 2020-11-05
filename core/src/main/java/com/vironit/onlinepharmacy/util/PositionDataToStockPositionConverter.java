package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.model.StockPosition;
import org.springframework.stereotype.Component;

@Component
public class PositionDataToStockPositionConverter implements Converter<StockPosition, PositionDto>{
    @Override
    public StockPosition convert(PositionDto positionDto) {
        StockPosition stockPosition=new StockPosition();
        stockPosition.setQuantity(positionDto.getQuantity());
        return stockPosition;
    }
}