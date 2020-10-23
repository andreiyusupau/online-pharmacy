package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductCategoryDao;
import com.vironit.onlinepharmacy.dto.ProductCategoryCreateData;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;

import java.util.Collection;

public class BasicProductCategoryService implements ProductCategoryService {

    private final ProductCategoryDao productCategoryDao;

    public BasicProductCategoryService(ProductCategoryDao productCategoryDao) {
        this.productCategoryDao = productCategoryDao;
    }

    @Override
    public void update(ProductCategory productCategory) {
        productCategoryDao.update(productCategory);
    }

    @Override
    public long add(ProductCategoryCreateData productCategoryCreateData) {
        return productCategoryDao.add(new ProductCategory(0,productCategoryCreateData.getName(),productCategoryCreateData.getDescription()));
    }

    @Override
    public ProductCategory get(long id) {
        return productCategoryDao.get(id)
                .orElseThrow(() -> new ProductServiceException("Can't get product category. Product category with id " + id + " not found."));
    }

    @Override
    public Collection<ProductCategory> getAll() {
        return productCategoryDao.getAll();
    }

    @Override
    public void remove(long id) {
productCategoryDao.remove(id);
    }
}
