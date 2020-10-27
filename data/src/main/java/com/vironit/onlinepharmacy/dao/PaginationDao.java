package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface PaginationDao<T> {

    long getTotalElements();

    Collection<T> getPage(int currentPage, int pageLimit);
}
