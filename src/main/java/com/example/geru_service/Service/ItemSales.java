package com.example.geru_service.Service;

public class ItemSales {
    private String itemName;
    private long unitPrice;
    private long quantity;

    public ItemSales(String itemName, long unitPrice, long quantity) {
        this.itemName = itemName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }

    public long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(long unitPrice) { this.unitPrice = unitPrice; }

    public long getQuantity() { return quantity; }
    public void setQuantity(long quantity) { this.quantity = quantity; }
    public void addQuantity(long qty) { this.quantity += qty; }

    public long getRevenue() { return unitPrice * quantity; }
}
