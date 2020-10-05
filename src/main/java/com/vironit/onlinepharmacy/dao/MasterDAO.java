package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface MasterDAO<T> {
    Collection<T> getAllSlaves(long masterId);
}
