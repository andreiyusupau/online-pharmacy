package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ProductCategoryDto {

    @NotBlank
    @Size(min = 2, max = 50)
    private final String name;
    @NotBlank
    @Size(min = 2, max = 500)
    private final String description;
    private long id;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProductCategoryDto(@JsonProperty("name") String name,
                              @JsonProperty("description") String description) {
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
