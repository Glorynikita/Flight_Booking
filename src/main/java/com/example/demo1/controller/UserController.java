package com.example.demo1.controller;

import com.example.demo1.dto.ApiResponse;
import com.example.demo1.dto.UserRequestDto;
import com.example.demo1.dto.UserResponseDto;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import static com.example.demo1.constants.CommonConstants.DELETED;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<UserResponseDto> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return userService.getUsers(filter, pageable);
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponseDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/add")
    public ApiResponse<UserResponseDto> addUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.addUser(userRequestDto);
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(id,userRequestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
       userService.deleteUser(id);
       return DELETED;

    }

}
