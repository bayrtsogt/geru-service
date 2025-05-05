package com.example.geru_service.DAO;

import com.example.geru_service.Entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDAO extends JpaRepository<ImageData, String> {
}
