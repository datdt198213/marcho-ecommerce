package com.ncc.mapstruct.dto.order;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderStatusDto {
    private int id;
    private StatusDto statusDto;
}
