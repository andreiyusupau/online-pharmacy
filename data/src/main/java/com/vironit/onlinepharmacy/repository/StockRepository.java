package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.StockPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockPosition,Long> {
}
