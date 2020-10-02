package com.vironit.onlinepharmacy.dao;

import java.util.Collection;
import java.util.Optional;

public interface DAO<T> {

    long add(T t);

    Optional<T> get(long id);

    Collection<T> getAll();

    boolean update(T t);

    boolean remove(long id);

}
