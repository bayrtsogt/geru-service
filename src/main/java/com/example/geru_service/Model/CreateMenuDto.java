package com.example.geru_service.Model;


import lombok.Data;

@Data
public class CreateMenuDto {

    private String name;
    private String type;         // e.g. "Eats", "Drinks", "Alcohol"
    private Long parentId;       // null for top-level menus
    private Boolean activeFlag = true;

    public CreateMenuDto() {
    }

    public CreateMenuDto(String name, String type, Long parentId, Boolean activeFlag) {
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return "CreateMenuDto{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", parentId=" + parentId +
                ", activeFlag=" + activeFlag +
                '}';
    }
}

