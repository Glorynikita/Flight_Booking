package com.example.demo1.service;

import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.UserProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.demo1.constants.MessageConstants.NOTFOUND;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserProfileRepo userProfileRepo;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserProfile user = userProfileRepo.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException(NOTFOUND));
        if(user==null){
            throw new UsernameNotFoundException(NOTFOUND);
        }

        return User.withUsername(user.getName())
                .password(user.getPassword())
                .authorities("ROLE_" +user.getRole())
                .build();
    }
}
