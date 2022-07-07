package com.ncc.mapstruct.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ncc.mapstruct.dto.CategoryDto;
import com.ncc.mapstruct.dto.color.ProductColorDto;
import com.ncc.mapstruct.dto.color.ProductColorWithoutSize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductViewDto {
    private String id;

    private String name;

    private String brand;

    private double star;

    @JsonProperty("price")
    private double priceOut;

    private int totalQuantity;

    private String description;

    @JsonProperty("category")
    private CategoryDto categoryDto;

    @JsonProperty("thumbnail")
    private List<ProductColorWithoutSize> productColorDtos;
}
