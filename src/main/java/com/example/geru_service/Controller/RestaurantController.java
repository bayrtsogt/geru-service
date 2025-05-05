package com.example.geru_service.Controller;

import com.example.geru_service.DAO.RestaurantDAO;
import com.example.geru_service.Entity.Restaurant;
import com.example.geru_service.Model.Message;
import com.example.geru_service.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin(origins = "*")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantDAO restaurantDAO;


    @GetMapping("/{restaurantId}")
    public ResponseEntity getById(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantDAO.getById(restaurantId).getOwnerId());
    }

    @GetMapping("/info/{restaurantId}")
    public ResponseEntity getRestaurantInfo(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantDAO.getRestaurantById(restaurantId));
    }
    @PostMapping("/create")
    public Message createRestaurant(
            @RequestParam("name") String name,
            @RequestParam("ownerId") String ownerId,
            @RequestParam("phone") String phone,
            @RequestParam("location") String location,
            @RequestParam("profileImage") MultipartFile profileImage,
            @RequestParam("coverImage") MultipartFile coverImage,
            @RequestParam("tableCount") String tableCount
    ) {
        return restaurantService.createRestaurant(
                name, ownerId, phone, location, profileImage, coverImage, tableCount
        );
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity getByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(restaurantService.getRestaurantsByOwner(ownerId));
    }
    @GetMapping("/delete/{restaurantId}")
    public ResponseEntity deleteRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(restaurantService.deleteRestaurant(restaurantId));
    }
}

