package com.vironit.onlinepharmacy.service;

public interface CrudService<T, S, U> extends ImmutableService<T,S>{
    void update(U u);
}
