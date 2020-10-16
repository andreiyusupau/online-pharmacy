package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class CollectionBasedProductDao implements ProductDao {

    private final IdGenerator idGenerator;
    private final Collection<Product> productList = new ArrayList<>();

    public CollectionBasedProductDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Product product) {
        long id = idGenerator.getNextId();
        product.setId(id);
        boolean successfulAdd = productList.add(product);
        return successfulAdd ? id : -1L;
    }

    @Override
    public Optional<Product> get(long id) {
        return productList.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    @Override
    public Collection<Product> getAll() {
        return productList;
    }

    @Override
    public boolean update(Product product) {
        return remove(product.getId()) && productList.add(product);
    }

    @Override
    public boolean remove(long id) {
        return productList.removeIf(product -> product.getId() == id);
    }

    @Override
    public int getTotalElements() {
        return productList.size();
    }

    @Override
    public Collection<Product> getPage(int currentPage, int pageLimit) {
        return productList.stream()
                .skip(currentPage*pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }
}
