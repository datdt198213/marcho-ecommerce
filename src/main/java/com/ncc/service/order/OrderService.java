package com.ncc.service.order;

import com.ncc.mapstruct.dto.order.OrderDetailDto;
import com.ncc.mapstruct.dto.order.OrderItemDto;

import java.util.List;

public interface OrderService {
    List<OrderDetailDto> getOrderDetails(int userId, int statusId);
    List<OrderDetailDto> getOrderDetails(int statusId);
    OrderDetailDto save(OrderDetailDto orderDetailDto);
    OrderItemDto save(OrderItemDto orderItemDto);
    void save(int userId);
    OrderItemDto merge(OrderItemDto orderItemDto);
    OrderDetailDto merge(OrderDetailDto orderDetailDto);
    void remove(int orderItemId);
}
