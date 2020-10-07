package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

@Deprecated
public interface MasterDAO<T> {
    Collection<T> getAllSlaves(long masterId);
}
