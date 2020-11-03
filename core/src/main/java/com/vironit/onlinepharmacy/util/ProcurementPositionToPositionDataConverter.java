package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.PositionData;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import org.springframework.stereotype.Component;

@Component
public class ProcurementPositionToPositionDataConverter implements Converter<PositionData, ProcurementPosition>{
    @Override
    public PositionData convert(ProcurementPosition procurementPosition) {
        return new PositionData(procurementPosition.getId(),procurementPosition.getProduct().getId(), procurementPosition.getQuantity());
    }
}
