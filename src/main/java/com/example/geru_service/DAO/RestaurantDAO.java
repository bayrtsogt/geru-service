package com.example.geru_service.DAO;

import com.example.geru_service.Entity.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantDAO extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByOwnerId(String ownerId);
    @Modifying                 // mark this as an update/delete operation
    @Transactional
    @Query("delete from Restaurant r where r.id = :id")
    int deleteByIdCustom(@Param("id") Long id);

    @Query("select a from Restaurant a where a.id = ?1  ")
    Restaurant getRestaurantById(Long restaurantId);
}
