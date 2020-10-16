package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface SlaveDao<T> {

    boolean addAll(Collection<T> t);

    Collection<T> getAllByOwnerId(long id);

    boolean removeAllByOwnerId(long id);

}
