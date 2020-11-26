package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.StockPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<StockPosition,Long> {
    Optional<StockPosition> findByProduct_Id(long id);
}
