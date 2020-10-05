package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface SlaveDAO<T> {
    Collection<T> getAllByOwnerId(long id);
}
