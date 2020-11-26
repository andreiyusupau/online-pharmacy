package com.vironit.onlinepharmacy.dao;

/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository}
 */
@Deprecated
public interface CrudDao<T> extends ImmutableDao<T> {
    boolean update(T t);
}
