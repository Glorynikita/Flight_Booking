package com.example.demo1.service;

import com.example.demo1.dto.ApiResponse;
import com.example.demo1.dto.UserRequestDto;
import com.example.demo1.dto.UserResponseDto;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.User_Profile;
import com.example.demo1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.example.demo1.constants.CommonConstants.*;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;


    public Page<UserResponseDto> getUsers(String keyword, Pageable pageable) {
        Specification<User_Profile> spec = (root, query, cb) -> {
            if (keyword != null && !keyword.isEmpty()) {
                String like = "%" + keyword.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("email")), like),
                        cb.like(cb.lower(root.get("gender")), like)
                );
            }
            return null;
        };
        return userRepo.findAll(spec, pageable).map(Mapper::toUserDto);
    }


    public ApiResponse<UserResponseDto> getUserById(Long id) {
        UserResponseDto dto = Mapper.toUserDto(userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
        return new ApiResponse<>(FOUND,dto);
    }


    public ApiResponse<UserResponseDto> addUser(UserRequestDto userRequestDto) {
        User_Profile userProfile = userRepo.save(Mapper.toUserEntity(userRequestDto));
        UserResponseDto userResponseDto = Mapper.toUserDto(userProfile);
        return new ApiResponse<>(ADDED,userResponseDto);
    }


    public ApiResponse<UserResponseDto> updateUser(Long id, UserRequestDto userRequestDto) {
        User_Profile userProfile = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userProfile.setName(userRequestDto.getName());
        userProfile.setGender(userRequestDto.getGender());
        userProfile.setPhone(userRequestDto.getPhone());
        userProfile.setEmail(userRequestDto.getEmail());
        userRepo.save(userProfile);
        UserResponseDto dto = Mapper.toUserDto(userProfile);
        return new ApiResponse<>(UPDATED,dto);
    }


    public String deleteUser(Long id) {
        userRepo.deleteById(id);
        return DELETED;
    }

    
}
