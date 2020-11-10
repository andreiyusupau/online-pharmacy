package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.time.Instant;

public class RecipeDto {

    @NotBlank
    @Size(min = 2, max = 500)
    private final String description;
    @Min(1)
    private final int quantity;
    @Positive
    private final long productId;
    @NotNull
    @FutureOrPresent
    private final Instant validThru;
    @Positive
    private final long orderPositionId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RecipeDto(@JsonProperty("description") String description,
                     @JsonProperty("quantity") int quantity,
                     @JsonProperty("productId") long productId,
                     @JsonProperty("validThru") Instant validThru,
                     @JsonProperty("orderPositionId") long orderPositionId) {
        this.description = description;
        this.quantity = quantity;
        this.productId = productId;
        this.validThru = validThru;
        this.orderPositionId = orderPositionId;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getProductId() {
        return productId;
    }

    public Instant getValidThru() {
        return validThru;
    }

    public long getOrderPositionId() {
        return orderPositionId;
    }
}
