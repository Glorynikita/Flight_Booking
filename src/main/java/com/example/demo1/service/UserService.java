package com.example.demo1.service;

import com.example.demo1.dto.UserRequestDto;
import com.example.demo1.dto.UserResponseDto;
import com.example.demo1.exception.DuplicateUserException;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.User_Profile;
import com.example.demo1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepo userRepo;
    @Autowired private Mapper mapper;

    public Page<UserResponseDto> getUsers(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

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


//Adding users without validating the duplicates

//    public ApiResponse<UserResponseDto> addUser(UserRequestDto userRequestDto) {
//        User_Profile userProfile = userRepo.save(Mapper.toUserEntity(userRequestDto));
//        UserResponseDto userResponseDto = Mapper.toUserDto(userProfile);
//        return new ApiResponse<>(ADDED,userResponseDto);
//    }


    public UserResponseDto getUserById(Long id) {
        return mapper.toUserDto(userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponseDto addUser(UserRequestDto dto) {
        Optional<User_Profile> existing = userRepo.findByEmailAndPhone(dto.getEmail(), dto.getPhone());
        if (existing.isPresent()) {
            throw new DuplicateUserException("User already exists.");
        }
        return mapper.toUserDto(userRepo.save(mapper.toUserEntity(dto)));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto dto) {
        User_Profile user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        user.setGender(dto.getGender());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        return mapper.toUserDto(userRepo.save(user));
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}











