package com.vironit.onlinepharmacy.service;

public interface CrudService<T, S> extends ImmutableService<T, S> {
    void update(T t);
}
