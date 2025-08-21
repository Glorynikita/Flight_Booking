package com.example.demo1.service;

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

    public String verify(UserProfile userProfile) {
        UserProfile userPro = userProfileRepo.findByName(userProfile.getName())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (encoder.matches(userProfile.getPassword(), userPro.getPassword())) {
            return jwtService.generateToken(userPro.getName());
        } else {
            throw new RuntimeException("Invalid password..");
        }
    }

}