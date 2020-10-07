package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProductDAO;
import com.vironit.onlinepharmacy.model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedProductDAO implements ProductDAO {

    private final Collection<Product> productList=new ArrayList<>();
    private long currentId = 0;

    @Override
    public long add(Product product) {
        product.setId(currentId);
        currentId++;
        productList.add(product);
        return product.getId();
    }

    @Override
    public Optional<Product> get(long id) {
        return productList.stream()
                .filter(product -> product.getId()==id)
                .findFirst();
    }

    @Override
    public Collection<Product> getAll() {
        return productList;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public boolean remove(long id) {
        return false;
    }
}
