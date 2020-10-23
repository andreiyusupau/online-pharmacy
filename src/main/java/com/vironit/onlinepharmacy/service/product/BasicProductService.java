package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.dto.ProductCreateData;
import com.vironit.onlinepharmacy.dto.ProductUpdateData;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import com.vironit.onlinepharmacy.util.Converter;

import java.util.Collection;

public class BasicProductService implements ProductService {

    private final ProductDao productDAO;
    private final ProductCategoryService productCategoryService;
    private final Converter<Product,ProductCreateData> productCreateDataToProductConverter;

    public BasicProductService(ProductDao productDAO, ProductCategoryService productCategoryService, Converter<Product, ProductCreateData> productCreateDataToProductConverter) {
        this.productDAO = productDAO;
        this.productCategoryService = productCategoryService;
        this.productCreateDataToProductConverter = productCreateDataToProductConverter;
    }

    @Override
    public long add(ProductCreateData productCreateData) {
        ProductCategory productCategory= productCategoryService.get(productCreateData.getProductCategoryId());
        Product product= productCreateDataToProductConverter.convert(productCreateData);
        product.setProductCategory(productCategory);
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
    public void update(ProductUpdateData productUpdateData) {
        ProductCategory productCategory= productCategoryService.get(productUpdateData.getProductCategoryId());
        Product product= productCreateDataToProductConverter.convert(productCreateData);
        product.setProductCategory(productCategory);
        productDAO.update(product);
    }

    @Override
    public void remove(long id) {
        productDAO.remove(id);
    }
}
