package com.vironit.onlinepharmacy.util;

import com.vironit.onlinepharmacy.dto.ProductCategoryDto;
import com.vironit.onlinepharmacy.model.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryDataToProductCategoryConverter implements Converter<ProductCategory, ProductCategoryDto> {
    @Override
    public ProductCategory convert(ProductCategoryDto productCategoryDto) {
        ProductCategory productCategory=new ProductCategory();
        productCategory.setName(productCategoryDto.getName());
        productCategory.setDescription(productCategoryDto.getDescription());
        return productCategory;
    }
}
