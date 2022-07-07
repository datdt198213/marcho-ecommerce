package com.ncc.mapstruct.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ncc.mapstruct.dto.CategoryDto;
import com.ncc.mapstruct.dto.color.ProductColorDto;
import com.ncc.mapstruct.dto.inventory.InventoryWithSizeDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailDto {
    private String id;

    private String name;

    private String brand;

    private double star;

    @JsonProperty(namespace = "price")
    private double priceOut;

    private String description;

    private int totalQuantity;

    @JsonProperty("colors")
    private List<ProductColorDto> productColorDtos;

    @JsonProperty("category")
    private CategoryDto categoryDto;
}
