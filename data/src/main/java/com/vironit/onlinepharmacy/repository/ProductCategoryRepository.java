package com.vironit.onlinepharmacy.repository;

import com.vironit.onlinepharmacy.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long> {
}