package com.vironit.onlinepharmacy.dto;

import java.util.List;

public class ProcurementData {

    private final long ownerId;
    private final List<ProcurementPositionData> procurementPositionDataList;

    public ProcurementData(long ownerId, List<ProcurementPositionData> procurementPositionDataList) {
        this.ownerId = ownerId;
        this.procurementPositionDataList = procurementPositionDataList;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<ProcurementPositionData> getProcurementPositionDataList() {
        return procurementPositionDataList;
    }
}
