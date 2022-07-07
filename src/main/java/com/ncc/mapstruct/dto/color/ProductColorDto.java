package com.ncc.mapstruct.dto.color;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ncc.mapstruct.dto.inventory.InventoryWithSizeDto;
import com.ncc.mapstruct.dto.size.SizeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductColorDto {
    private int id;

    private double star;

    private String[] urlImages;

    @JsonProperty("color")
    private ColorDto colorDto;

    @JsonProperty("sizes")
    private List<InventoryWithSizeDto> inventoryDtos;

    public String toStringUrlImage(){
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : urlImages) {
            stringBuilder.append(s + ",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    public static String toStringUrlImage(List<String> urlImages){
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : urlImages) {
            stringBuilder.append(s + ",");
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }
}
