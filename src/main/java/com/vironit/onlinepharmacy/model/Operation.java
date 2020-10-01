package com.vironit.onlinepharmacy.model;

import java.time.Instant;

public class Operation {

    private long id;
    private Instant date;
    private OperationType type;
    private OperationStatus status;
    private long useId;

    public Operation() {
    }

    public Operation(long id, Instant date, OperationType type, OperationStatus status, long useId) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.status = status;
        this.useId = useId;
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

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public long getUseId() {
        return useId;
    }

    public void setUseId(long useId) {
        this.useId = useId;
    }


}
