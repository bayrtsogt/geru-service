package com.example.geru_service.Entity;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "User", schema = "geru")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "register_number")
    private String registerNumber;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "mail_address")
    private String mailAddress;

    @Column(name = "password")
    private String password;

    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;
}
