package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.ProductCreateData;
import com.vironit.onlinepharmacy.model.Product;

public class ProductCreateDataToProductConverter implements Converter<Product, ProductCreateData> {
    @Override
    public Product convert(ProductCreateData productCreateData) {
        return new Product(0,productCreateData.getName(),productCreateData.getPrice(),null,productCreateData.isRecipeRequired());
    }
}
