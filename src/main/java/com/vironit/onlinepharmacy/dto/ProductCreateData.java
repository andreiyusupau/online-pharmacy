package com.vironit.onlinepharmacy.dto;

import java.math.BigDecimal;

public class ProductCreateData {

    private final String name;
    private final BigDecimal price;
    private final long productCategoryId;
    private final boolean recipeRequired;

    public ProductCreateData(String name, BigDecimal price, long productCategoryId, boolean recipeRequired) {
        this.name = name;
        this.price = price;
        this.productCategoryId = productCategoryId;
        this.recipeRequired = recipeRequired;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getProductCategoryId() {
        return productCategoryId;
    }

    public boolean isRecipeRequired() {
        return recipeRequired;
    }
}
