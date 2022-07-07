package com.ncc.repository;

import com.ncc.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    @Modifying
    @Query(
            value = "INSERT INTO order_detail (user_id, total, created_at) " +
                            "VALUES (:userId, 0.0, NOW())",
            nativeQuery = true)

    void save(@Param("userId") int userId);

    @Modifying
    @Query(value = "INSERT INTO order_status (order_id, status_id, created_at) " +
            "VALUES (:orderId, :statusId, NOW())",
    nativeQuery = true)
    void saveOrderStatus(@Param("orderId") int orderId,@Param("statusId") int statusId);

    @Query(value = "SELECT od.* FROM order_detail od " +
            "JOIN order_item oi ON od.id = oi.order_id " +
            "JOIN order_status os ON od.id = os.order_id ",
            nativeQuery = true)
    List<OrderDetail> getOrderDetailByUserAndOrderStatus(@Param("userId") int userId, @Param("orderStatusId") int orderStatusId);

    @Query(value = "SELECT od FROM OrderDetail od " +
            "JOIN od.orderStatuses ods " +
            "JOIN ods.status odss " +
            "WHERE odss.id = :orderStatusId")
    List<OrderDetail> getOrderDetailByUserAndOrderStatus(@Param("orderStatusId") int orderStatusId);
}
