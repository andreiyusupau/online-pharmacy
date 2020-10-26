package com.vironit.onlinepharmacy.dao;

import java.util.Collection;
import java.util.Optional;

public interface ImmutableDao<T> {

    long add(T t);

    Optional<T> get(long id);

    Collection<T> getAll();

    boolean remove(long id);
}
