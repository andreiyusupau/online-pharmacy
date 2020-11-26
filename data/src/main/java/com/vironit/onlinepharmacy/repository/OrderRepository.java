package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByOwner_Id(long id);
}
