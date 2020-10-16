package com.vironit.onlinepharmacy.dao;

import java.util.Collection;

public interface PaginationDao<T> {

    int getTotalElements();

    Collection<T> getPage(int currentPage, int pageLimit);
}
