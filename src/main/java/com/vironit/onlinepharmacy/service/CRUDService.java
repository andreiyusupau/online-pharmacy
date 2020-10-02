package com.vironit.onlinepharmacy.service;

import java.util.Collection;

public interface CRUDService<T> {

    long add(T t);

    T get(long id);

    Collection<T> getAll();

    void update(T t);

    void remove(long id);
}
