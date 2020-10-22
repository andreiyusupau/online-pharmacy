package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class OrderCreateData {
    private final long ownerId;
    private final List<PositionData> operationPositionDataList;

    public OrderCreateData(long ownerId, List<PositionData> operationPositionDataList) {
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
