package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.dto.ProcurementDto;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.service.CrudService;

public interface ProcurementService extends CrudService<ProcurementDto, Procurement> {

    void approveProcurement(long id);

    void completeProcurement(long id);

    void cancelProcurement(long id);
}
