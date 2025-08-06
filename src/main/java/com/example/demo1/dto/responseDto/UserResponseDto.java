package com.example.demo1.dto.responseDto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String gender;
    private String phone;
    private String email;
}
