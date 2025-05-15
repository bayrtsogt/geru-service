package com.example.geru_service.Service;

import com.example.geru_service.DAO.ItemDAO;
import com.example.geru_service.DAO.OrderDAO;
import com.example.geru_service.DAO.RestaurantDAO;
import com.example.geru_service.Entity.Item;
import com.example.geru_service.Entity.Orders;
import com.example.geru_service.Entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired private RestaurantDAO restaurantDAO;
    @Autowired private OrderDAO orderDAO;
    @Autowired private ItemDAO itemDAO;

    @Override
    public OwnerReport generateReport(Long restaurantId) {
        Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
        List<Orders> orders = orderDAO.getOrdersByRestaurantId(restaurantId);

        long totalOrders = orders.size();
        long totalRevenue = 0L;

        Map<Long, ItemSales> salesMap = new HashMap<>();
        Map<String, Long> revenueByCategory = new HashMap<>();

        for (Orders order : orders) {
            Item item = itemDAO.findById(order.getProductId());
            long qty = order.getCount();
            long rev = item.getPrice() * qty;
            totalRevenue += rev;

            salesMap.computeIfAbsent(item.getId(), id -> new ItemSales(item.getName(), item.getPrice(), 0L))
                    .addQuantity(qty);

            String category = item.getType() == null ? "Бусад" : item.getType();
            revenueByCategory.merge(category, rev, Long::sum);
        }

        List<ItemSales> allSales = new ArrayList<>(salesMap.values());
        allSales.sort(Comparator.comparingLong(ItemSales::getQuantity).reversed());

        OwnerReport report = new OwnerReport();
        report.setRestaurantName(restaurant.getName());
        report.setTotalOrders(totalOrders);
        report.setTotalRevenue(totalRevenue);
        report.setRevenueByCategory(revenueByCategory);
        report.setItemsSales(allSales);

        return report;
    }
}