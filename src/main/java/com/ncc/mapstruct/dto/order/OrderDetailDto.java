package com.ncc.mapstruct.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailDto {
    private double total;
    private int userId;
    private int paymentId;
    private String createdAt;
    private List<OrderItemDto> orderItemDtos;
    private int statusId;
}