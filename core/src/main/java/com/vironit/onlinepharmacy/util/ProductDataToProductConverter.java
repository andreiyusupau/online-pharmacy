package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.ProductData;
import com.vironit.onlinepharmacy.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDataToProductConverter implements Converter<Product, ProductData>{
    @Override
    public Product convert(ProductData productData) {
        Product product=new Product();
        product.setName(productData.getName());
        product.setPrice(productData.getPrice());
        product.setRecipeRequired(productData.isRecipeRequired());
        return product;
    }
}
