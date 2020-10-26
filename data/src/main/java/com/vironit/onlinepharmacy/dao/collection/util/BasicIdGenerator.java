package com.vironit.onlinepharmacy.dao.collection.util;

public class BasicIdGenerator implements IdGenerator {

    private long id = 0;

    @Override
    public long getNextId() {
        id++;
        return id;
    }
}
