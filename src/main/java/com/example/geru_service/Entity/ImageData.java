package com.example.geru_service.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "images", schema = "geru")
public class ImageData {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;
    private String type;

    @Lob
    @Column(name = "data", columnDefinition = "LONGBLOB") // MySQL: LONGBLOB (up to 4 GB)
    private byte[] data;
}
