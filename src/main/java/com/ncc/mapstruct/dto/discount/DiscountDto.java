package com.ncc.mapstruct.dto.discount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiscountDto {
    private int id;
    private String name;
    private int discountPercent;
}
