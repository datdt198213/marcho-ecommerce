package com.ncc.repository;

import com.ncc.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    @Query(value = "SELECT avg(f.vote) FROM Feedback f WHERE f.product.id = :prodId")
    Optional<Double> calculateAverageStar(@Param("prodId") String prodId);

    @Query(value = "SELECT f FROM Feedback f WHERE f.product.id = :prodId",
    countQuery = "SELECT COUNT(f.id) FROM Feedback f WHERE f.product.id = :prodId")
    Page<Feedback> findByProductAndColor(@Param("prodId") String prodId, Pageable pageable);
}
