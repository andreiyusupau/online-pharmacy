package com.vironit.onlinepharmacy.dao;

public interface CrudDao<T> extends ImmutableDao<T> {
    boolean update(T t);
}
