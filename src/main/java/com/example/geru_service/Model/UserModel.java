package com.example.geru_service.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserModel {
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("pin")
    private String pin;

    @JsonProperty("phoneNumber")
    private Long phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("bankAccountNumber")
    private Long bankAccountNumber;

    @JsonProperty("bankId")
    private Long bankId;
}
