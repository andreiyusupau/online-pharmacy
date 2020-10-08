package com.vironit.onlinepharmacy.dao.collection;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CollectionBasedProductDao implements ProductDao {

    private final IdGenerator idGenerator;
    private final Collection<Product> productList=new ArrayList<>();

    public CollectionBasedProductDao(IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public long add(Product product) {
        long id=idGenerator.getNextId();
        product.setId(id);
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
        if(remove(product.getId())){
           return productList.add(product);
        }else {
            return false;
        }
    }

    @Override
    public boolean remove(long id) {
        return productList.removeIf(product -> product.getId()==id);
    }
}
