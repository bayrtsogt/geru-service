package com.example.geru_service.DAO;

import com.example.geru_service.Entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDAO extends JpaRepository<Orders, String> {
    @Query("select a from Orders a where a.isPaid = 0")
    List<Orders> getNotPaid();

    @Query("select a from Orders a where a.invoiceId = ?1")
    List<Orders>  findByInvoiceId(Long invoiceId);

    @Query("select a from Orders a where a.restaurantId = ?1 and a.isPaid = 0L and a.status = 1L")
    List<Orders> getOrdersByRestaurantId(Long restaurantId);

    @Query("select a from Orders a where a.id = ?1")
    Orders getOrderById(Long orderId);
}
