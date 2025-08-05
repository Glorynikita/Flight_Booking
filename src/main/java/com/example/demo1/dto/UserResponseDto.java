package com.example.demo1.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserResponseDto {
    private Long userId;
    private String name;
    private String gender;
    private String phone;
    private String email;
}
