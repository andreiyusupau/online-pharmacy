package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dto.ProductCategoryDto;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.repository.ProductCategoryRepository;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProductCategoryService implements ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final Converter<ProductCategory, ProductCategoryDto> productCategoryDataToProductCategoryConverter;


    public BasicProductCategoryService(ProductCategoryRepository productCategoryRepository, Converter<ProductCategory, ProductCategoryDto> productCategoryDataToProductCategoryConverter) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryDataToProductCategoryConverter = productCategoryDataToProductCategoryConverter;
    }

    @Override
    public void update(ProductCategoryDto productCategoryDto) {
        productCategoryRepository.save(productCategoryDataToProductCategoryConverter.convert(productCategoryDto));
    }

    @Override
    public long add(ProductCategoryDto productCategoryDto) {
        return productCategoryRepository.save(productCategoryDataToProductCategoryConverter.convert(productCategoryDto))
                .getId();
    }

    @Override
    public ProductCategory get(long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductServiceException("Can't get product category. Product category with id " + id + " not found."));
    }

    @Override
    public Collection<ProductCategory> getAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public void remove(long id) {
        productCategoryRepository.deleteById(id);
    }
}
