package com.example.demo1.dto.requestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String name;
    private String gender;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)\\.com$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long, contain one uppercase, one lowercase, one digit, and one special character")
    private String password;

}
