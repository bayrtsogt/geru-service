package com.example.geru_service.Service;

import com.example.geru_service.DAO.ImageDAO;
import com.example.geru_service.Entity.ImageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageStorageService {
    @Autowired
    private ImageDAO imageRepository;

    public String saveImage(MultipartFile file) throws IOException {
        ImageData image = new ImageData();
        image.setName(file.getOriginalFilename());
        image.setType(file.getContentType());
        image.setData(file.getBytes());              // raw bytes in
        return imageRepository.save(image).getId();
    }

    public ImageData getImage(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found: " + id));
    }
}
