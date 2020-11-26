package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.StockPosition;

import java.util.Optional;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.StockRepository}
 */
@Deprecated
public interface StockDao extends CrudDao<StockPosition>, PaginationDao<StockPosition> {
    Optional<StockPosition> getByProductId(long productId);
}
