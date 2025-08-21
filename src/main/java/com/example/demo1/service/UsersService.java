package com.example.demo1.service;

import com.example.demo1.dto.responseDto.AuthResponseDto;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserProfileRepo userProfileRepo;
    private final JWTService jwtService;
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthResponseDto verify(UserProfile userProfile) {
        UserProfile userPro = userProfileRepo.findByName(userProfile.getName())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (encoder.matches(userProfile.getPassword(), userPro.getPassword())) {
            String accessToken = jwtService.generateToken(userPro.getName());
            String refreshToken = jwtService.generateRefreshToken(userPro.getName());
            return new AuthResponseDto(accessToken,refreshToken);
        }
        else {
            throw new RuntimeException("Invalid password..");
        }
    }

}