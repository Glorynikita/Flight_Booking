package com.example.demo1.service;

import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UserProfileRepo userProfileRepo;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
//    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String verify(UserProfile userProfile) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userProfile.getName(), userProfile.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(userProfile.getName());
        }
        return "fail";
    }


}













//public String verify(UserProfile userProfile) {
//    UserProfile userProfile1 = userProfileRepo.findByName(userProfile.getName())
//            .orElseThrow(() -> new RuntimeException("Invalid username or password"));
//    if (encoder.matches(userProfile.getPassword(), userProfile1.getPassword())) {
//        return jwtService.generateToken(userProfile1.getName()); //token generation
//    } else {
//        throw new RuntimeException("Invalid password!");
//    }
//}