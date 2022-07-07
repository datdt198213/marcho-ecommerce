package com.ncc.mapstruct.dto.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ncc.mapstruct.dto.color.ColorDto;
import com.ncc.mapstruct.dto.size.SizeDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private int id;

    private int quantity;

    @JsonProperty("color")
    private ColorDto colorDto;

    @JsonProperty("size")
    private SizeDto sizeDto;
}
