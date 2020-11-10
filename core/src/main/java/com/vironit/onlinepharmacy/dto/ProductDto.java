package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductDto {

    @NotBlank
    @Size(min = 2, max = 80)
    private final String name;
    @NotNull
    @Positive
    private final BigDecimal price;
    @Positive
    private final long productCategoryId;
    private final boolean recipeRequired;
    private long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductDto(@JsonProperty("name") String name,
                      @JsonProperty("price") BigDecimal price,
                      @JsonProperty("productCategoryId") long productCategoryId,
                      @JsonProperty("recipeRequired") boolean recipeRequired) {
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
