package com.example.geru_service.Service;

import java.util.List;
import java.util.Map;

public class OwnerReport {
    private String restaurantName;
    private long totalOrders;
    private long totalRevenue;
    private Map<String, Long> revenueByCategory;
    private List<ItemSales> itemsSales;

    public String getRestaurantName() { return restaurantName; }
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public long getTotalOrders() { return totalOrders; }
    public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }

    public long getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(long totalRevenue) { this.totalRevenue = totalRevenue; }

    public Map<String, Long> getRevenueByCategory() { return revenueByCategory; }
    public void setRevenueByCategory(Map<String, Long> revenueByCategory) { this.revenueByCategory = revenueByCategory; }

    public List<ItemSales> getItemsSales() { return itemsSales; }
    public void setItemsSales(List<ItemSales> itemsSales) { this.itemsSales = itemsSales; }
}
