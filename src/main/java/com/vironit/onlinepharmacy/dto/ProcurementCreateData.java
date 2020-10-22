package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class ProcurementCreateData {

    private final long ownerId;
    private final List<PositionData> operationPositionDataList;

    public ProcurementCreateData(long ownerId, List<PositionData> operationPositionDataList) {
        this.ownerId = ownerId;
        this.operationPositionDataList = operationPositionDataList;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<PositionData> getOperationPositionDataList() {
        return operationPositionDataList;
    }
}
