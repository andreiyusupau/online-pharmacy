package com.vironit.onlinepharmacy.model;

import java.util.List;

public class Warehouse {

    private long id;
    private List<Position> positions;

    public Warehouse() {
    }

    public Warehouse(long id, List<Position> positions) {
        this.id = id;
        this.positions = positions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }
}
