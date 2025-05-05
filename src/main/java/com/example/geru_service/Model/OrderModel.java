package com.example.geru_service.Model;

import lombok.Data;

@Data
public class OrderModel {
    private Long restaurantId;
    private Long tableId;
    private Long productId;
    private Long count;
}
