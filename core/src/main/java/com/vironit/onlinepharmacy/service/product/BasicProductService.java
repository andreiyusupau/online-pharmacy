package com.vironit.onlinepharmacy.service.product;

import com.vironit.onlinepharmacy.dto.ProductDto;
import com.vironit.onlinepharmacy.model.Product;
import com.vironit.onlinepharmacy.model.ProductCategory;
import com.vironit.onlinepharmacy.repository.ProductRepository;
import com.vironit.onlinepharmacy.service.exception.ProductServiceException;
import com.vironit.onlinepharmacy.util.converter.Converter;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BasicProductService implements ProductService {

    private final ProductRepository productRepository;
    private final Converter<Product, ProductDto> productDataToProductConverter;

    public BasicProductService(ProductRepository productRepository, Converter<Product, ProductDto> productDataToProductConverter) {
        this.productRepository = productRepository;
        this.productDataToProductConverter = productDataToProductConverter;
    }

    @Override
    public long add(ProductDto productDto) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productDto.getProductCategoryId());
        Product product = productDataToProductConverter.convert(productDto);
        product.setProductCategory(productCategory);
        return productRepository.save(product)
                .getId();
    }

    @Override
    public Product get(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductServiceException("Can't get product. Product with id " + id + " not found."));
    }

    @Override
    public Collection<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public void update(ProductDto productDto) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productDto.getProductCategoryId());
        Product product = productDataToProductConverter.convert(productDto);
        product.setProductCategory(productCategory);
        productRepository.save(product);
    }

    @Override
    public void remove(long id) {
        productRepository.deleteById(id);
    }
}
