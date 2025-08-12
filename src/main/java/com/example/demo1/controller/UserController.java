package com.example.demo1.controller;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    public UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @GetMapping("/all")
    public PagedModel<EntityModel<UserResponseDto>> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<UserResponseDto> users = userService.getUsers(filter, page, size, sortBy, sortDir);
        return userAssembler.toPagedModel(users, filter, page, size, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public EntityModel<UserResponseDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userService.getUserById(id));
    }

    @PostMapping("/add")
    public EntityModel<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto dto) {
        return userAssembler.toModel(userService.addUser(dto));
    }

    @PutMapping("/{id}")
    public EntityModel<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userAssembler.toModel(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }
}


