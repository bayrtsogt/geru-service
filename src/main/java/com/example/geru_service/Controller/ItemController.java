package com.example.geru_service.Controller;


import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.ProductModel;
import com.example.geru_service.Service.ImageStorageService;
import com.example.geru_service.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ItemController {
    @Autowired private ItemService itemService;

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Message> createProduct(
            @RequestParam("name")        String name,
            @RequestParam("ownerId")     Long ownerId,
            @RequestParam("price")       Long price,
            @RequestParam("isSharefood") Long isSharefood,
            @RequestParam("type")        String type,
            @RequestParam(value="alcoholPercent", defaultValue="0") Integer alcoholPercent,
            @RequestPart("img")          MultipartFile img
    ) throws IOException {
        return ResponseEntity.ok(
                itemService.createItem(img, name, price, isSharefood, ownerId, type, alcoholPercent)
        );
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<List<ProductModel>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(itemService.getProducts(ownerId));
    }

    @GetMapping("fromUser/{ownerId}")
    public ResponseEntity<List<ProductModel>> getByOwnerFromUser(@PathVariable Long ownerId) {
        return ResponseEntity.ok(itemService.getProductsFromUser(ownerId));
    }

    @GetMapping("/{type}/{ownerId}")
    public ResponseEntity<List<ProductModel>> getProductByType(@PathVariable Long ownerId, @PathVariable String type) {
        return ResponseEntity.ok(itemService.getProductByType(ownerId, type));
    }
    @GetMapping("/delete/{ownerId}")
    public ResponseEntity deleteProduct(@PathVariable Long ownerId) {
        return ResponseEntity.ok(itemService.deleteProduct(ownerId));
    }
    @GetMapping("/active/{productId}")
    public ResponseEntity activeProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(itemService.activeProduct(productId));
    }
}

