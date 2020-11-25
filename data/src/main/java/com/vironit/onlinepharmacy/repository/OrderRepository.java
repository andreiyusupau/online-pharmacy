package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
