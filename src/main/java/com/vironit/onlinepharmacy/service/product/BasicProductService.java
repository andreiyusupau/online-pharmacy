package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;

import java.util.Collection;

public class BasicProductService implements ProductService {

    private final ProductDao productDAO;

    public BasicProductService(ProductDao productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public long add(Product product) {
        return productDAO.add(product);
    }

    @Override
    public Product get(long id) {
        return productDAO.get(id)
                .orElseThrow(() -> new ProductServiceException("Can't get product. Product with id " + id + " not found."));
    }

    @Override
    public Collection<Product> getAll() {
        return productDAO.getAll();
    }

    @Override
    public void update(Product product) {
        productDAO.update(product);
    }

    @Override
    public void remove(long id) {
        productDAO.remove(id);
    }
}
