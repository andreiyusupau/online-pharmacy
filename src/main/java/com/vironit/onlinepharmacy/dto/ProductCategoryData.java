package com.vironit.onlinepharmacy.dto;

public class ProductCategoryData {
    private final String name;
    private final String description;

    public ProductCategoryData(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
