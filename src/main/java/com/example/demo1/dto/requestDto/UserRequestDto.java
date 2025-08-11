package com.example.demo1.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String password;
}
