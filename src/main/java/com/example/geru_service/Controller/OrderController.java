package com.example.geru_service.Controller;

import com.example.geru_service.Model.ProductModel;
import com.example.geru_service.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/kitchen/{restaurantId}")
    public ResponseEntity getKitchenOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getKitchenOrders(restaurantId));
    }

    @GetMapping("/bar/{restaurantId}")
    public ResponseEntity getBarOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getBarOrders(restaurantId));
    }

    @GetMapping("/done/{orderId}")
    public void doneOrder(@PathVariable Long orderId) {
        orderService.doneOrder(orderId);
    }
}
