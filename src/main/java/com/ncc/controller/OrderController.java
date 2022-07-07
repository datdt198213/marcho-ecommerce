package com.ncc.controller;

import com.ncc.mapstruct.dto.order.OrderDetailDto;
import com.ncc.mapstruct.dto.order.OrderItemDto;
import com.ncc.mapstruct.dto.product.ProductDetailDto;
import com.ncc.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ncc.contants.Constant.IN_CART_STATUS_ID;

@RestController
@RequestMapping("api/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailDto>> getOrders(@RequestHeader("userId") int userId){
        List<OrderDetailDto> dtos = orderService.getOrderDetails(userId, IN_CART_STATUS_ID);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<OrderItemDto> save(@RequestBody OrderItemDto orderItemDto) {
        return null;
    }

    @PutMapping
    public ResponseEntity<OrderItemDto> update(@RequestBody OrderItemDto orderItemDto) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {

        return ResponseEntity.ok("Remove successfully. ");
    }
}
