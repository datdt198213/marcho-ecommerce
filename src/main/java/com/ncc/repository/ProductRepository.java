package com.ncc.repository;

import com.ncc.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id " +
            "ORDER BY p.price_out ASC",
            countQuery = "SELECT COUNT(p.id) FROM product p ",
            nativeQuery = true)
    Optional<Page<Product>> findByOrderByPriceOutAsc(Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id " +
            "ORDER BY p.price_out DESC",
            countQuery = "SELECT COUNT(p.id) FROM product p ",
            nativeQuery = true)
    Optional<Page<Product>> findByOrderByPriceOutDesc(Pageable pageable);

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id " +
            "WHERE p.price_out BETWEEN :start AND :end " +
            "ORDER BY p.price_out",
            countQuery = "SELECT COUNT(p.id) FROM product p ",
            nativeQuery = true)
    Optional<Page<Product>> findByPriceOutBetween(@Param("start") double start, @Param("end") double end, Pageable pageable);


    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id " +
            "WHERE p.name LIKE %:name% " +
            "ORDER BY p.price_out",
            countQuery = "SELECT COUNT(p.id) FROM product p ",
            nativeQuery = true)
    Optional<Page<Product>> findByNameContainingIgnoreCase(@Param("name") String name,
                                                           Pageable pageable);

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.productColors pc WHERE pc.color.id = :colorId",
            countQuery = "SELECT count(p) FROM Product p JOIN p.productColors")
    Optional<Page<Product>> getProductFromColor(@Param("colorId") int colorId, Pageable pageable);

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.productColors pc WHERE p.id = :prodId")
    Optional<Product> getProducts(@Param("prodId") String prodId);

    List<Product> findByName(String name);

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id ",

            countQuery = "SELECT COUNT(p.id) FROM product p ", nativeQuery = true)
    Optional<Page<Product>> getProductView(Pageable pageable);

    @Query(value = "SELECT avg(price_out) FROM product", nativeQuery = true)
    double calculateAveragePriceOut();

    @Query(value = "SELECT DISTINCT p.* FROM product p " +
            "JOIN product_discount pd ON pd.prod_id = p.id " +
            "JOIN product_color pc ON pc.prod_id = p.id " +
            "WHERE cate_id = :cateId",

            countQuery = "SELECT COUNT(p.id) FROM product p ", nativeQuery = true)
    Optional<Page<Product>> getProductFromCategory(@Param("cateId") String cateId, Pageable pageable);

    @Query(value = "SELECT p FROM Product p JOIN FETCH p.inventories i" +
            " WHERE p.id= :prodId AND i.color.id = :colorId")
    Optional<Product> getProductById(@Param("prodId") String prodId, @Param("colorId") int colorId);

    @Query(value = "SELECT p FROM Product p " +
            "JOIN FETCH p.inventories i " +
            "WHERE p.id= :prodId")
    Optional<Product> getProductById(@Param("prodId") String prodId);

    @Query(value = "SELECT DISTINCT p FROM Product p JOIN FETCH p.productColors pc " +
            "WHERE ( p.category.id IN :cateIds ) " +
            "AND (pc.color.id = :colorId) " +
            "AND (p.name LIKE %:name%) " +
            "AND (p.priceOut BETWEEN :startPrice AND :endPrice) ",
            countQuery = "SELECT DISTINCT COUNT(p.id) FROM Product p JOIN p.category c " +
                    "JOIN p.productColors pc " +
                    "WHERE ( p.category.id IN :cateIds ) " +
                    "AND ( p.name LIKE %:name% )" +
                    "AND (p.priceOut BETWEEN :startPrice AND :endPrice) " +
                    "AND (pc.color.id = :colorId) ")
    Optional<Page<Product>> filter(@Param("cateIds") List<String> cateIds,
                                   @Param("colorId") Integer colorId,
                                   @Param("startPrice") Double startPrice,
                                   @Param("endPrice") Double endPrice,
                                   @Param("name") String name,
                                   Pageable pageable);

    @Query(value = "SELECT DISTINCT p FROM Product p JOIN p.category c " +
            "JOIN p.productColors pc " +
            "JOIN p.inventories i " +
            "WHERE ( c.id IN :cateIds ) " +
            "AND (p.name LIKE %:name%) " +
            "AND (p.priceOut BETWEEN :startPrice AND :endPrice) ",
            countQuery = "SELECT DISTINCT COUNT(p.id) FROM Product p JOIN p.category c " +
                    "WHERE ( c.id IN :cateIds ) " +
                    "AND (p.name LIKE %:name%) " +
                    "AND (p.priceOut BETWEEN :startPrice AND :endPrice) ")
    Optional<Page<Product>> filter(@Param("cateIds") List<String> cateIds,
                                   @Param("startPrice") Double startPrice,
                                   @Param("endPrice") Double endPrice,
                                   @Param("name") String name,
                                   Pageable pageable);

    @Query("SELECT MAX(p.priceOut) FROM Product p")
    Optional<Double> findMaxPriceOut();

    @Modifying
    @Query("UPDATE Product p SET p.status = FALSE, p.deletedAt = current_date WHERE p.id = :id")
    void disableProduct(@Param("id") String id);
}
