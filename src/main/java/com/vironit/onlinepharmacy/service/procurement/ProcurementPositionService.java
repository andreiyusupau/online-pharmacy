package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.model.ProcurementPosition;
import com.vironit.onlinepharmacy.service.CrudService;
import com.vironit.onlinepharmacy.service.SlaveService;

public interface ProcurementPositionService extends CrudService<ProcurementPosition,ProcurementPosition,ProcurementPosition> , SlaveService<ProcurementPosition> {

}
