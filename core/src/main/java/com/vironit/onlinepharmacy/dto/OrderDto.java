package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderDto {

    private long id;
    private final long ownerId;
    private final List<PositionDto> positionDtoList;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public OrderDto(@JsonProperty("ownerId")long ownerId,
                    @JsonProperty("positionDtoList")List<PositionDto> positionDtoList) {
        this.ownerId = ownerId;
        this.positionDtoList = positionDtoList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public List<PositionDto> getPositionDataList() {
        return positionDtoList;
    }
}
