package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ProductDto {

    private long id;
    private final String name;
    private final BigDecimal price;
    private final long productCategoryId;
    private final boolean recipeRequired;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductDto(@JsonProperty("name")String name,
                      @JsonProperty("price")BigDecimal price,
                      @JsonProperty("productCategoryId")long productCategoryId,
                      @JsonProperty("recipeRequired")boolean recipeRequired) {
        this.name = name;
        this.price = price;
        this.productCategoryId = productCategoryId;
        this.recipeRequired = recipeRequired;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
