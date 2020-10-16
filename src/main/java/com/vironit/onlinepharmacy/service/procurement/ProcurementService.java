package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dto.ProcurementCreateData;
import com.vironit.onlinepharmacy.dto.ProcurementUpdateData;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.service.CRUDService;

public interface ProcurementService extends CRUDService<ProcurementCreateData, Procurement, ProcurementUpdateData> {

    void approveProcurement(long id);

    void completeProcurement(long id);

    void cancelProcurement(long id);
}
