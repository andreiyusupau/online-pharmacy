package com.vironit.onlinepharmacy.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.List;

public class ProcurementDto {

    private long id;
    @Positive
    private final long ownerId;
    @NotEmpty
    private final List<@Valid PositionDto> positionDtoList;


    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ProcurementDto(@JsonProperty("ownerId") long ownerId,
                          @JsonProperty("positionDtoList") List<PositionDto> positionDtoList) {
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
