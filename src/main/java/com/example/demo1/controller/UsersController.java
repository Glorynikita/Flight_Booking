package com.example.demo1.controller;

import com.example.demo1.dto.responseDto.AuthResponseDto;
import com.example.demo1.model.UserProfile;
import com.example.demo1.service.JWTService;
import com.example.demo1.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final JWTService jwtService;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody UserProfile userProfile) {
        return usersService.verify(userProfile);
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtService.extractName(refreshToken);

            if (jwtService.validateRefreshToken(refreshToken, username)) {
                return jwtService.generateToken(username);
            } else {
                throw new RuntimeException("Invalid refresh token");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid refresh token");
        }
    }
}


