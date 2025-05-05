package com.example.geru_service.Service;

import com.example.geru_service.DAO.RestaurantDAO;
import com.example.geru_service.Entity.Restaurant;
import com.example.geru_service.Model.Message;
import com.example.geru_service.Model.RestaurantModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@Service
@Transactional
public class RestaurantService {

    @Autowired
    private RestaurantDAO restaurantDAO;

    @Autowired
    private ImageStorageService imageStorageService;


    public Message createRestaurant(
            String name, String ownerId, String phone, String location,
            MultipartFile profileImage, MultipartFile coverImage, String tableCount
    ) {
        Message msg = new Message();

        try {
            // save images → get their IDs
            String profileImageId = imageStorageService.saveImage(profileImage);
            String coverImageId   = imageStorageService.saveImage(coverImage);

            // create entity
            Restaurant r = new Restaurant();
            r.setName(name);
            r.setOwnerId(ownerId);
            r.setPhone(phone);
            r.setLocation(location);
            r.setTableCount(tableCount);
            r.setProfileImage(profileImageId);
            r.setCoverImage(coverImageId);

            restaurantDAO.save(r);

            // build response data with Base64 payloads
            r.setProfileImage(imageStorageService.getImage(profileImageId).getId());
            r.setCoverImage(imageStorageService.getImage(coverImageId).getId());

            msg.setStatus(200L);
            msg.setMessage("Ресторан амжилттай бүртгэгдлээ.");
            msg.setData(r);
        } catch (Exception e) {
            msg.setStatus(500L);
            msg.setMessage("Зургийг хадгалах үед алдаа гарлаа.");
            e.printStackTrace();
        }

        return msg;
    }


    public List<RestaurantModel> getRestaurantsByOwner(String ownerId) {
        List<RestaurantModel> resModels = new ArrayList<>();
        List<Restaurant> list = restaurantDAO.findByOwnerId(ownerId);
        list.forEach(r -> {
            try {
                RestaurantModel restaurantModel = new RestaurantModel();
                restaurantModel.setId(r.getId());
                restaurantModel.setOwnerId(r.getOwnerId());
                restaurantModel.setName(r.getName());
                restaurantModel.setLocation(r.getLocation());
                restaurantModel.setPhone(r.getPhone());
                restaurantModel.setTableCount(r.getTableCount());
                restaurantModel.setMenuId(r.getMenuId());

                restaurantModel.setCoverImage(imageStorageService.getImage(r.getCoverImage()).getData());
                restaurantModel.setProfileImage(imageStorageService.getImage(r.getProfileImage()).getData());
                resModels.add(restaurantModel);
            } catch (RuntimeException ignored) { }
        });
        return resModels;
    }
    public int deleteRestaurant(Long restaurantId) {
        return restaurantDAO.deleteByIdCustom(restaurantId);
    }
}
