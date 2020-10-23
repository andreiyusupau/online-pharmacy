package com.vironit.onlinepharmacy.dto;

public class ProductCategoryCreateData {
    private final String name;
    private final String description;

    public ProductCategoryCreateData(String name, String description) {
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
