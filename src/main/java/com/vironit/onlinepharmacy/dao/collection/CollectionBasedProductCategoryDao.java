package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProductCategoryDao;
import com.vironit.onlinepharmacy.model.ProductCategory;

import java.util.Collection;
import java.util.Optional;

public class CollectionBasedProductCategoryDao implements ProductCategoryDao {
    @Override
    public boolean update(ProductCategory productCategory) {
        return false;
    }

    @Override
    public long add(ProductCategory productCategory) {
        return 0;
    }

    @Override
    public Optional<ProductCategory> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<ProductCategory> getAll() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }
}
