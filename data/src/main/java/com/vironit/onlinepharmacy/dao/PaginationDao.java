package com.vironit.onlinepharmacy.dao;

import java.util.Collection;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository}
 */
@Deprecated
public interface PaginationDao<T> {

    long getTotalElements();

    Collection<T> getPage(int currentPage, int pageLimit);
}
