package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
