package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderPositionRepository extends JpaRepository<OrderPosition,Long> {
    List<OrderPosition> findByOrder_Id(long id);
    boolean deleteByOrder_Id(long id);
}
