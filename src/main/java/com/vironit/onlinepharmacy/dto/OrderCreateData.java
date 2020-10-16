package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class OrderCreateData {
    private final long ownerId;
    private final List<OperationPositionData> operationPositionDataList;

    public OrderCreateData(long ownerId, List<OperationPositionData> operationPositionDataList) {
        this.ownerId = ownerId;
        this.operationPositionDataList = operationPositionDataList;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<OperationPositionData> getOperationPositionDataList() {
        return operationPositionDataList;
    }
}
