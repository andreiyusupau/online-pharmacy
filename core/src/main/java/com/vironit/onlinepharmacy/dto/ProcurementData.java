package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class ProcurementData {

    private final long id;
    private final long ownerId;
    private final List<PositionData> positionDataList;

    public ProcurementData(long id, long ownerId, List<PositionData> positionDataList) {
        this.id = id;
        this.ownerId = ownerId;
        this.positionDataList = positionDataList;
    }

    public long getId() {
        return id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<PositionData> getPositionDataList() {
        return positionDataList;
    }
}
