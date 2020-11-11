package com.vironit.onlinepharmacy.util.converter;

import com.vironit.onlinepharmacy.dto.ProductDto;
import com.vironit.onlinepharmacy.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataToProductConverter implements Converter<Product, ProductDto>{
    @Override
    public Product convert(ProductDto productDto) {
        Product product=new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setRecipeRequired(productDto.isRecipeRequired());
        return product;
    }
}
