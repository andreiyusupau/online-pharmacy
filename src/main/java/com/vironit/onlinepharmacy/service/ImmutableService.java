package com.vironit.onlinepharmacy.service;

import java.util.Collection;

public interface ImmutableService<T, S> {
    long add(T t);

    S get(long id);

    Collection<S> getAll();

    void remove(long id);
}
