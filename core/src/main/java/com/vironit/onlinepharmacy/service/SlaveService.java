package com.vironit.onlinepharmacy.service;

import java.util.Collection;

public interface SlaveService<T> {
    boolean addAll(Collection<T> positionData);

    Collection<T> getAllByOwnerId(long id);

    boolean removeAllByOwnerId(long id);
}
