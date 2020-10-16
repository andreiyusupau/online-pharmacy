package com.vironit.onlinepharmacy.dao.collection;

public class BasicIdGenerator implements IdGenerator {

    private long id = 0;

    @Override
    public long getNextId() {
        id++;
        return id;
    }
}
