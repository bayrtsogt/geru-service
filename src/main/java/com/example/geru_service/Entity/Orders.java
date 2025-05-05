package com.example.geru_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "orders", schema = "geru")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "table_id", nullable = false)
    private Long tableId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "count", nullable = false)
    private Long count;
    @Column(name = "status", nullable = false)
    private Long status;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "is_paid")
    private Long isPaid;

}
