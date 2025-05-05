package com.example.geru_service.Model;

import lombok.Data;

@Data
public class Message {
    Long status;
    String message;
    Object data;
}
