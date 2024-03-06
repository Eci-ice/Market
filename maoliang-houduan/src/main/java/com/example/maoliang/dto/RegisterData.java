package com.example.maoliang.dto;

import lombok.Data;

@Data
public class RegisterData {
    private String username;
    private String password;

    private String role;
    private String question;
    private String answer;
    private String address;
    private String phone;

}
