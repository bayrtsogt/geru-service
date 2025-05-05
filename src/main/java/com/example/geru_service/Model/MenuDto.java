package com.example.geru_service.Model;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class MenuDto {
    private Long id;
    private String name;
    private String type;                // e.g. "Eats", "Drinks", "Alcohol"
    private Long parentId;              // null for top-level menus
    private Boolean activeFlag;
    private List<MenuDto> children;     // nested sub-menus
    private List<ProductDto> products;  // products assigned to this menu

    public MenuDto() {
        this.activeFlag = true;
        this.children = new ArrayList<>();
        this.products = new ArrayList<>();
    }

    public MenuDto(Long id,
                   String name,
                   String type,
                   Long parentId,
                   Boolean activeFlag,
                   List<MenuDto> children,
                   List<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.activeFlag = activeFlag != null ? activeFlag : true;
        this.children = children != null ? children : new ArrayList<>();
        this.products = products != null ? products : new ArrayList<>();
    }
}