package com.example.geru_service.DAO;
import com.example.geru_service.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserDAO extends JpaRepository<User, Long> {
    User findAllByPhoneNumber(Long phoneNumber);
    User findByRegisterNumber(String registerNumber);
    @Query("select a from User a where a.mailAddress = ?1")
    User findAllByMailAddress(String mailAddress);

    @Query("select a from User a where a.id = ?1")
    User findUserId(Long id);
}

