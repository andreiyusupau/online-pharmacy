package com.vironit.onlinepharmacy.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "procurements")
public class Procurement extends Operation {

    @Enumerated(value = EnumType.STRING)
    private ProcurementStatus procurementStatus;

    public Procurement() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Procurement)) return false;
        if (!super.equals(o)) return false;
        Procurement that = (Procurement) o;
        return procurementStatus == that.procurementStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), procurementStatus);
    }
}
