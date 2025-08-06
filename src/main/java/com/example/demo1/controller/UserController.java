package com.example.demo1.controller;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo1.constants.CommonConstants.DELETED;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private UserAssembler userAssembler;

    /**
     * lit all the users
     * @param filter
     * @param page
     * @param size
     * @param sortBy
     * @param sortDir
     * @param pagedAssembler
     * @return
     */


    @GetMapping("/users")
    public ResponseEntity<PagedModel<EntityModel<UserResponseDto>>> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            PagedResourcesAssembler<UserResponseDto> pagedAssembler // Must removed
    ) {
        Page<UserResponseDto> users = userService.getUsers(filter, page, size, sortBy, sortDir);
        PagedModel<EntityModel<UserResponseDto>> model = pagedAssembler.toModel(users, userAssembler);
        return ResponseEntity.ok(model);
    }


    @GetMapping("/{id}")
    public EntityModel<UserResponseDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userService.getUserById(id));
    }

    @PostMapping("/add")
    public EntityModel<UserResponseDto> addUser(@RequestBody UserRequestDto dto) {
        return userAssembler.toModel(userService.addUser(dto));
    }

    @PutMapping("/{id}")
    public EntityModel<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return userAssembler.toModel(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(DELETED);
    }
}

