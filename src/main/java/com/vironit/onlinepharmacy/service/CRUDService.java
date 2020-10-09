package com.vironit.onlinepharmacy.service;

import java.util.Collection;

public interface CRUDService<T, S, U> {

    long add(T t);

    S get(long id);

    Collection<S> getAll();

    void update(U u);

    void remove(long id);
}
