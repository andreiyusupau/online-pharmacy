package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class OrderCreateData {

    private final long ownerId;
    private final List<PositionData> positionDataList;

    public OrderCreateData(long ownerId, List<PositionData> positionDataList) {
        this.ownerId = ownerId;
        this.positionDataList = positionDataList;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<PositionData> getPositionDataList() {
        return positionDataList;
    }
}
