package com.vironit.onlinepharmacy.service;

import com.vironit.onlinepharmacy.model.Procurement;
import com.vironit.onlinepharmacy.model.User;

public interface ProcurementService extends CRUDService<Procurement>{

    long createProcurement(User user);

    void approveProcurement(long id);

    void completeProcurement(long id);

    void cancelProcurement(long id);
}
