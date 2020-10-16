package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class OrderUpdateData extends OrderCreateData {

    private final long id;

    public OrderUpdateData(long id, long ownerId, List<OperationPositionData> operationPositionDataList) {
        super(ownerId, operationPositionDataList);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
