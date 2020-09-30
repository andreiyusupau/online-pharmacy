package com.vironit.onlinepharmacy.model;

import java.time.Instant;
import java.util.List;

public class Operation {

    private long id;
    private Instant date;
    private List<Position> positions;
    private User owner;
    private OperationType type;

    public Operation() {
    }

    public Operation(long id, Instant date, List<Position> positions, User owner, OperationType type) {
        this.id = id;
        this.date = date;
        this.positions = positions;
        this.owner = owner;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
}
