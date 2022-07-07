package com.ncc.repository;

import com.ncc.model.ProductDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDiscountRepository extends JpaRepository<ProductDiscount,Integer> {
    ProductDiscount findByCouponIgnoreCase(String coupon);
}
