package com.ncc.repository;

import com.ncc.model.Color;
import com.ncc.model.Product;
import com.ncc.model.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductColorRepository extends JpaRepository<ProductColor, Integer> {
    @Modifying
    @Query(value = "INSERT INTO product_color (`color_id`, `prod_id`, `url_image`) " +
            "VALUES (:colorId, :prodId , :urlImage)", nativeQuery = true)
    void insert(@Param("prodId") String prodId, @Param("colorId") int colorId, @Param("urlImage") String urlImage);

//    @Modifying
//    @Query(value = "UPDATE product_color " +
//            "SET color_id = :colorId AND prod_id = :prod_id AND url_image = :urlImage",
//            nativeQuery = true)
//    void update(@Param("prodId") String prodId, @Param("colorId") int colorId, @Param("urlImage") String urlImage);

//    ProductColor findByProductAndColor(Product product, Color color);
}
