package com.example.geru_service.Model;

import lombok.Data;

@Data
public class RestaurantModel {
    private Long id;
    private String name;
    private String ownerId;
    private String menuId;
    private String phone;
    private String location;
    private byte[] profileImage;
    private byte[] coverImage;
    private String tableCount;
}
