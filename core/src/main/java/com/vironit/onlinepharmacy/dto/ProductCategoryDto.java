package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductCategoryDto {

    private long id;
    private final String name;
    private final String description;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductCategoryDto(@JsonProperty("name")String name,
                              @JsonProperty("description")String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
