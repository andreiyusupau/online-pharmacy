package com.vironit.onlinepharmacy.dto;

import java.math.BigDecimal;

public class ProductUpdateData extends ProductCreateData {

    private final long id;

    public ProductUpdateData(long id, String name, BigDecimal price, long productCategoryId, boolean recipeRequired) {
        super(name, price, productCategoryId, recipeRequired);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
