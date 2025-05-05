package com.example.geru_service.Model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InvoiceResponseData {
    private Long id;
    private String status;
    private Long amount;
    private String description;
    private String customerId;
    private String number;
    private Long projectId;
    private String url;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}