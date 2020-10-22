package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class ProcurementUpdateData extends ProcurementCreateData {

    private final long id;

    public ProcurementUpdateData(long id, long ownerId, List<PositionData> operationPositionDataList) {
        super(ownerId, operationPositionDataList);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
