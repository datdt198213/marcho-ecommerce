package com.ncc.mapstruct.dto.order;

import com.ncc.mapstruct.dto.inventory.InventoryDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private InventoryDto inventoryDto;
    private int orderId;
}