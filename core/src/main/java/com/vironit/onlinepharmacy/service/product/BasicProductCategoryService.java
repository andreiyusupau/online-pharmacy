package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dao.ProductCategoryDao;
import com.vironit.onlinepharmacy.dto.ProductCategoryDto;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import com.vironit.onlinepharmacy.util.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProductCategoryService implements ProductCategoryService {

    private final ProductCategoryDao productCategoryDao;
private final Converter<ProductCategory, ProductCategoryDto> productCategoryDataToProductCategoryConverter;


    public BasicProductCategoryService(ProductCategoryDao productCategoryDao, Converter<ProductCategory, ProductCategoryDto> productCategoryDataToProductCategoryConverter) {
        this.productCategoryDao = productCategoryDao;
        this.productCategoryDataToProductCategoryConverter = productCategoryDataToProductCategoryConverter;
    }

    @Override
    public void update(ProductCategoryDto productCategoryDto) {
        productCategoryDao.update(productCategoryDataToProductCategoryConverter.convert(productCategoryDto));
    }

    @Override
    public long add(ProductCategoryDto productCategoryDto) {
        return productCategoryDao.add(productCategoryDataToProductCategoryConverter.convert(productCategoryDto));
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
