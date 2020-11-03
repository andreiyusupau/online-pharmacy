package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductDao;
import com.vironit.onlinepharmacy.dto.ProductData;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProductService implements ProductService {

    private final ProductDao productDAO;
    private final ProductCategoryService productCategoryService;
    private final Converter<Product, ProductData> productDataToProductConverter;

    public BasicProductService(ProductDao productDAO, ProductCategoryService productCategoryService, Converter<Product, ProductData> productDataToProductConverter) {
        this.productDAO = productDAO;
        this.productCategoryService = productCategoryService;
        this.productDataToProductConverter = productDataToProductConverter;
    }

    @Override
    public long add(ProductData productData) {
        ProductCategory productCategory= new ProductCategory();
        productCategory.setId(productData.getProductCategoryId());
        Product product= productDataToProductConverter.convert(productData);
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
    public void update(ProductData productData) {
        ProductCategory productCategory= new ProductCategory();
        productCategory.setId(productData.getProductCategoryId());
        Product product= productDataToProductConverter.convert(productData);
        product.setProductCategory(productCategory);
        productDAO.update(product);
    }

    @Override
    public void remove(long id) {
        productDAO.remove(id);
    }
}
