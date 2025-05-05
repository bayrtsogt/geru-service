package com.example.geru_service.Service;

import com.example.geru_service.DAO.ItemDAO;
import com.example.geru_service.Entity.Item;
import com.example.geru_service.Entity.Restaurant;
import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.ProductModel;
import com.example.geru_service.Model.RestaurantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemDAO itemDAO;

    @Autowired
    ImageStorageService imageStorageService;

    public Message createItem(MultipartFile img,
                              String name,
                              Long price,
                              Long isSharefood,
                              Long ownerId,
                              String type,
                              Integer alcoholPercent
    ) throws IOException {
        Item item = new Item();
        item.setName(name);
        item.setPrice(price);
        item.setIsSharefood(isSharefood);
        item.setImageId(imageStorageService.saveImage(img));
        item.setOwnerId(ownerId);
        item.setType(type);
        item.setActiveFlag(1);
        item.setAlcoholPercent(alcoholPercent != null ? alcoholPercent : 0);
        itemDAO.save(item);

        Message msg = new Message();
        msg.setData(item);
        msg.setStatus(200L);
        msg.setMessage("Амжилттай хадгаллаа");
        return msg;
    }


    public List<ProductModel> getProductsFromUser(Long ownerId) {
        List<Item> items = itemDAO.findByOwnerIdFromUser(ownerId);
        List<ProductModel> productModels = new ArrayList<>();
        for (Item item : items) {
            ProductModel pm = new ProductModel();
            pm.setId(item.getId());
            pm.setName(item.getName());
            pm.setPrice(item.getPrice());
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            pm.setOwnerId(item.getOwnerId());
            pm.setIsSharefood(item.getIsSharefood());
            pm.setImage(imageStorageService.getImage(item.getImageId()).getData());
            // ← set new fields
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            productModels.add(pm);
        }
        return productModels;
    }
    public List<ProductModel> getProducts(Long ownerId) {
        List<Item> items = itemDAO.findByOwnerId(ownerId);
        List<ProductModel> productModels = new ArrayList<>();
        for (Item item : items) {
            ProductModel pm = new ProductModel();
            pm.setId(item.getId());
            pm.setName(item.getName());
            pm.setPrice(item.getPrice());
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            pm.setOwnerId(item.getOwnerId());
            pm.setIsSharefood(item.getIsSharefood());
            pm.setImage(imageStorageService.getImage(item.getImageId()).getData());
            // ← set new fields
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            productModels.add(pm);
        }
        return productModels;
    }
    public int deleteProduct(Long ownerId) {
        return itemDAO.deleteProduct(ownerId);
    }
    public Item activeProduct(Long id) {
        Item item = itemDAO.findById(id);
        if(item.getActiveFlag() == 0){
            item.setActiveFlag(1);
        }
        else{
            item.setActiveFlag(0);
        }
        return itemDAO.save(item);
    }


    public List<ProductModel> getProductByType(Long ownerId, String type) {
        List<Item> items = itemDAO.findByOwnerIdAndType(ownerId, type);
        List<ProductModel> productModels = new ArrayList<>();
        for (Item item : items) {
            ProductModel pm = new ProductModel();
            pm.setId(item.getId());
            pm.setName(item.getName());
            pm.setPrice(item.getPrice());
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            pm.setOwnerId(item.getOwnerId());
            pm.setIsSharefood(item.getIsSharefood());
            pm.setActiveFlag(Long.valueOf(item.getActiveFlag()));
            pm.setImage(imageStorageService.getImage(item.getImageId()).getData());
            pm.setType(item.getType());
            pm.setAlcoholPercent(item.getAlcoholPercent());
            productModels.add(pm);
        }
        return productModels;
    }
}
