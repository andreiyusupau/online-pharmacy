package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.Product;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.ProductRepository}
 */
@Deprecated
public interface ProductDao extends CrudDao<Product>, PaginationDao<Product> {
}
