package com.example.geru_service.DAO;

import com.example.geru_service.Entity.Item;
import com.example.geru_service.Entity.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDAO extends JpaRepository<Item, String> {

    @Query("select a from Item a where a.ownerId = ?1")
    List<Item> findByOwnerId(Long ownerId);
    @Query("select a from Item a where a.ownerId = ?1 and a.activeFlag = 1")
    List<Item> findByOwnerIdFromUser(Long ownerId);
    @Query("select a from Item a where a.ownerId = ?1 and a.type = ?2")
    List<Item> findByOwnerIdAndType(Long ownerId, String type);


    @Query("select a from Item a where a.id = ?1")
    Item findById(Long productId);
    @Query("select a from Item a where a.id in ?1")
    List<Item> findAllById(List<Long> productIds);

    @Modifying
    @Transactional
    @Query("delete from Item a where a.id in ?1")
    int deleteProduct(Long ownerId);
}
