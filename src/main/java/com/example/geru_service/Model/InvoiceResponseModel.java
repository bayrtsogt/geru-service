package com.example.geru_service.Model;

import lombok.Data;

import java.util.Date;

@Data
public class InvoiceResponseModel {
    Long id;
    String status;
    Long amount;
    String description ;
    Long customer_id ;
    String number ;
    Long project_id ;
    String url ;
    Date due_date ;
    Date created_at ;
    Date updated_at;
}
