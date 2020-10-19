package com.vironit.onlinepharmacy.dao.jdbc;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;

import java.util.Collection;
import java.util.Optional;

public class JdbcProductDao implements ProductDao {

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public long add(Product product) {
        return 0;
    }

    @Override
    public Optional<Product> get(long id) {
        return Optional.empty();
    }

    @Override
    public Collection<Product> getAll() {
        return null;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public int getTotalElements() {
        return 0;
    }

    @Override
    public Collection<Product> getPage(int currentPage, int pageLimit) {
        return null;
    }
}
