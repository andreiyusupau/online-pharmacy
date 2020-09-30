package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class Operation {

    private long id;
    private Instant date;
    private OperationType type;
    private long user_id;

    public Operation() {
    }

    public Operation(long id, Instant date, OperationType type, long user_id) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.user_id = user_id;
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

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }
    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }


}
