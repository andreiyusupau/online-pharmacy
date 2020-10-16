package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Position;

import java.util.Optional;

public interface StockDao extends Dao<Position>, PaginationDao<Position> {

    Optional<Position> getByProductId(long productId);

}
