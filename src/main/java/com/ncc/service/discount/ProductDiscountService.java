package com.ncc.service.discount;

import com.ncc.mapstruct.dto.discount.ProductDiscountDto;

import java.util.List;

public interface ProductDiscountService {
    ProductDiscountDto getByCoupon(String coupon);
    List<ProductDiscountDto> getAll();
    ProductDiscountDto save(ProductDiscountDto productDiscountDto);
    ProductDiscountDto update(ProductDiscountDto productDiscountDto);
}
