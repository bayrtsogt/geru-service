package com.example.geru_service.Model;

import com.example.geru_service.Entity.Item;
import lombok.Data;

import java.util.List;

@Data
public class OrderModels {
    private Long tableNumber;
    private List<ProductModel> products;

}
