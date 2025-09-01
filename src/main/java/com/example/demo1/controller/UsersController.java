package com.example.demo1.controller;

import com.example.demo1.dto.responseDto.AuthResponseDto;
import com.example.demo1.model.UserProfile;
import com.example.demo1.service.JWTService;
import com.example.demo1.service.UsersService;
import com.example.demo1.translator.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;
    private final JWTService jwtService;
    private final Translator translator;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody UserProfile userProfile) {
        return usersService.verify(userProfile);
    }

    @PostMapping("/refresh")
    public String refresh(@RequestBody Map<String, String> request, Locale locale) {
        String refreshToken = request.get(translator.toLocale("refresh.token",locale));
        try {
            String username = jwtService.extractName(refreshToken);

            if (jwtService.validateRefreshToken(refreshToken, username)) {
                return jwtService.generateToken(username);
            } else {
                throw new RuntimeException(translator.toLocale("invalid.RToken",locale));
            }
        } catch (Exception e) {
            throw new RuntimeException(translator.toLocale("invalid.RToken",locale));
        }
    }
}


