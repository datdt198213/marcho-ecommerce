package com.ncc.mapstruct.dto.discount;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDiscountDto {
    private int id;
    private String tittle;
    private String coupon;
    private Date startAt;
    private Date expireAt;
}
