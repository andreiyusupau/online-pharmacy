package com.vironit.onlinepharmacy.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "procurement_positions")
public class ProcurementPosition extends Position {

    @ManyToOne
    private Procurement procurement;

    public ProcurementPosition() {

    }

    public ProcurementPosition(long id, int quantity, Product product, Procurement procurement) {
        super(id, quantity, product);
        this.procurement = procurement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcurementPosition)) return false;
        if (!super.equals(o)) return false;
        ProcurementPosition that = (ProcurementPosition) o;
        return procurement.equals(that.procurement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), procurement);
    }

    @Override
    public String toString() {
        return "ProcurementPosition{" +
                "procurement=" + procurement +
                '}';
    }

    public Procurement getProcurement() {
        return procurement;
    }
}
