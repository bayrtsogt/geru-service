package com.example.geru_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "restaurant", schema = "geru")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "menu_id")
    private String menuId;

    private String phone;

    private String location;
    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "cover_image")
    private String coverImage;


    @Column(name = "table_count")
    private String tableCount;
}
