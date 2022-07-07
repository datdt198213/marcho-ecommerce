package com.ncc.mapstruct.dto.color;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ncc.mapstruct.dto.inventory.InventoryWithSizeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProductColorWithoutSize {
    private int id;

    private String[] urlImages;

    @JsonProperty("color")
    private ColorDto colorDto;

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
