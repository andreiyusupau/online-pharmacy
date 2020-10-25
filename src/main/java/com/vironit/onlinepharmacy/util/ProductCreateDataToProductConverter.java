package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.ProductData;
import com.vironit.onlinepharmacy.model.Product;

public class ProductCreateDataToProductConverter implements Converter<Product, ProductData> {
    @Override
    public Product convert(ProductData productData) {
        return new Product(0, productData.getName(), productData.getPrice(),null, productData.isRecipeRequired());
    }
}
