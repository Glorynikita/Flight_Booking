package com.example.demo1.dto.responseDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Long userId;
    private String name;
    private String gender;
    private String phone;
    private String email;
}
