package com.example.geru_service.Model;

import java.math.BigDecimal;

public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private String imageId;
    private Long isSharefood;
    private Long ownerId;

    public ProductDto() {
        // default constructor
    }

    public ProductDto(Long id, String name, Long price, String imageId, Long isSharefood, Long ownerId) {
        this.id = id;
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.imageId = imageId;
        this.isSharefood = isSharefood;
        this.ownerId = ownerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Long getIsSharefood() {
        return isSharefood;
    }

    public void setIsSharefood(Long isSharefood) {
        this.isSharefood = isSharefood;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageId='" + imageId + '\'' +
                ", isSharefood=" + isSharefood +
                ", ownerId=" + ownerId +
                '}';
    }
}
