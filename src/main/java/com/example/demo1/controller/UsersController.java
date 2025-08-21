package com.example.demo1.controller;

import com.example.demo1.model.UserProfile;
import com.example.demo1.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/login")
    public String login(@RequestBody UserProfile userProfile) {
        return usersService.verify(userProfile);
    }

}


