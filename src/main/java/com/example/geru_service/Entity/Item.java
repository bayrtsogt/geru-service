package com.example.geru_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "item", schema = "geru")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "is_sharefood")
    private Long isSharefood;

    @Column(name = "image_id")
    private String imageId;

    private Long price;

    @Column(name = "owner_id")
    private Long ownerId;

    // ← NEW FIELDS ↓
    @Column(name = "type", nullable = false)
    private String type;              // "Eats" or "Drink"

    @Column(name = "alcohol_percent", nullable = false)
    private Integer alcoholPercent;   // 0 for soft drinks / Eats

    @Column(name = "active_flag", nullable = false)
    private Integer activeFlag;   // 0 / 1

    @Transient
    private Long count;
    @Transient
    private byte[] image;
    @Transient
    private Long orderId;
}
