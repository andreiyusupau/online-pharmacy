package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface DAO<T> {

    long add(T t);

    T get(long id);

    Collection<T> getAll();

    boolean update(T t);

    boolean remove(long id);

}
