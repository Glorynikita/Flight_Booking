package com.example.demo1.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String password;
}
