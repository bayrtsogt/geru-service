package com.example.geru_service.Model;

import lombok.Data;

@Data
public class ProductModel {
    private Long id;
    private String name;
    private Long price;
    private Long ownerId;
    private Long isSharefood;
    private Long activeFlag;
    private Long count;
    private Long orderId;
    private Long tableNumber;
    private byte[] image;
    private String type;
    private Integer alcoholPercent;
}
