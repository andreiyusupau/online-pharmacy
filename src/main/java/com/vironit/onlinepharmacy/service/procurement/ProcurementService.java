package com.vironit.onlinepharmacy.service.procurement;

import com.vironit.onlinepharmacy.model.OperationPosition;
import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.User;
import com.vironit.onlinepharmacy.service.CRUDService;

import java.util.Collection;

public interface ProcurementService extends CRUDService<Procurement> {

    long createProcurement(User user);

    void approveProcurement(long id);

    void completeProcurement(long id);

    void cancelProcurement(long id);

    boolean addAll(Collection<OperationPosition> positions);
}
