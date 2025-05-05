package com.example.geru_service.Service;

import com.example.geru_service.DAO.ItemDAO;
import com.example.geru_service.DAO.OrderDAO;
import com.example.geru_service.Entity.Item;
import com.example.geru_service.Entity.Orders;
import com.example.geru_service.Model.OrderModels;
import com.example.geru_service.Model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    ImageStorageService imageStorageService;


    public List<OrderModels> getKitchenOrders(Long restaurantId) {
        return getOrdersByType(restaurantId, "Eats");
    }

    public List<OrderModels> getBarOrders(Long restaurantId) {
        return getOrdersByType(restaurantId, "Drink");
    }

    /**
     * Generic routine: fetch all orders for the restaurant,
     * keep only those whose Item.type matches, group by table, then build models.
     * Also sets the transient count field on each Item.
     */
    public List<OrderModels> getOrdersByType(Long restaurantId, String desiredType) {
        // 1) fetch all orders
        List<Orders> allOrders = orderDAO.getOrdersByRestaurantId(restaurantId);
        if (allOrders.isEmpty()) {
            return Collections.emptyList();
        }

        // 2) cache Items so we don’t hit the DB repeatedly
        Map<Long, Item> itemCache = allOrders.stream()
                .map(Orders::getProductId)
                .distinct()
                .map(itemDAO::findById)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Item::getId, Function.identity()));

        // 3) filter by type, then group by tableId
        Map<Long, List<Orders>> ordersByTable = allOrders.stream()
                .filter(o -> {
                    Item i = itemCache.get(o.getProductId());
                    return i != null && desiredType.equals(i.getType());
                })
                .collect(Collectors.groupingBy(Orders::getTableId));

        // 4) build one OrderModels per table
        return ordersByTable.entrySet().stream()
                .map(entry -> {
                    Long tableId       = entry.getKey();        // ← this is your Orders.tableId
                    List<Orders> os    = entry.getValue();

                    // map each Orders → ProductModel
                    List<ProductModel> products = os.stream()
                            .map(o -> {
                                Item    it   = itemCache.get(o.getProductId());
                                ProductModel pm = new ProductModel();

                                pm.setId(         it.getId());
                                pm.setName(       it.getName());
                                pm.setPrice(       it.getPrice());
                                pm.setCount(      o.getCount());
                                pm.setImage(      imageStorageService.getImage(it.getImageId()).getData());
                                pm.setOrderId(    o.getId());
                                pm.setTableNumber(o.getTableId());       // ← also available here

                                return pm;
                            })
                            .collect(Collectors.toList());

                    OrderModels model = new OrderModels();
                    model.setTableNumber(tableId);               // ← sets OrderModels.tableNumber
                    model.setProducts(  products);
                    return model;
                })
                .collect(Collectors.toList());
    }


    public void doneOrder(Long orderId) {
        Orders orders = orderDAO.getOrderById(orderId);
        orders.setStatus(0L);
        orderDAO.save(orders);
    }
}
