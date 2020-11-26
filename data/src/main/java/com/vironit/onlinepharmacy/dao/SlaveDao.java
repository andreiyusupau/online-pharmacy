package com.vironit.onlinepharmacy.dao;

import java.util.Collection;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository}
 */
@Deprecated
public interface SlaveDao<T> {

    boolean addAll(Collection<T> t);

    Collection<T> getAllByOwnerId(long id);

    boolean removeAllByOwnerId(long id);

}
