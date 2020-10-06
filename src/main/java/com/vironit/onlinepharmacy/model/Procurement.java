package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class Procurement extends Operation {

    private ProcurementStatus procurementStatus;

    public Procurement() {
        super();
    }

    public Procurement(long id, Instant date, User owner, ProcurementStatus procurementStatus) {
        super(id, date, owner);
        this.procurementStatus = procurementStatus;
    }

    public ProcurementStatus getProcurementStatus() {
        return procurementStatus;
    }

    public void setProcurementStatus(ProcurementStatus procurementStatus) {
        this.procurementStatus = procurementStatus;
    }
}
