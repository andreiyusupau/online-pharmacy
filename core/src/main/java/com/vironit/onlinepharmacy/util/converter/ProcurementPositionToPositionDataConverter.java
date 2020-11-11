package com.vironit.onlinepharmacy.util.converter;

import com.vironit.onlinepharmacy.dto.PositionDto;
import com.vironit.onlinepharmacy.model.ProcurementPosition;
import org.springframework.stereotype.Component;

@Component
public class ProcurementPositionToPositionDataConverter implements Converter<PositionDto, ProcurementPosition>{
    @Override
    public PositionDto convert(ProcurementPosition procurementPosition) {
        return new PositionDto(procurementPosition.getProduct().getId(), procurementPosition.getQuantity());
    }
}
