package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.ProductCategoryData;
import com.vironit.onlinepharmacy.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryDataToProductCategoryConverter implements Converter<ProductCategory, ProductCategoryData> {
    @Override
    public ProductCategory convert(ProductCategoryData productCategoryData) {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setName(productCategoryData.getName());
        productCategory.setDescription(productCategoryData.getDescription());
        return productCategory;
    }
}
