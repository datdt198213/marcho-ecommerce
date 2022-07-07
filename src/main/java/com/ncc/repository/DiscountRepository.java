package com.ncc.repository;

import com.ncc.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Query("SELECT d FROM Discount d JOIN ProductDiscount pd " +
            "WHERE pd.product.id = :prodId")
    Discount findDiscountByProductId(@Param("prodId") String id);
}
