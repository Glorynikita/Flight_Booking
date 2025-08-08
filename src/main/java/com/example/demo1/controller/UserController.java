package com.example.demo1.controller;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo1.constants.CommonConstants.DELETED;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserAssembler userAssembler;

    public  UserController(UserService userService, UserAssembler userAssembler) {
        this.userService = userService;
        this.userAssembler = userAssembler;
    }

    @GetMapping
    public PagedModel<EntityModel<UserResponseDto>> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<UserResponseDto> users = userService.getUsers(filter, page, size, sortBy, sortDir);

        List<EntityModel<UserResponseDto>> entities = users.getContent().stream()
                .map(userAssembler::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(
                entities,
                new PagedModel.PageMetadata(
                        users.getSize(),
                        users.getNumber(),
                        users.getTotalElements(),
                        users.getTotalPages()
                ),
                linkTo(methodOn(UserController.class).getUsers(filter, page, size, sortBy, sortDir)).withSelfRel()
        );
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








//    @GetMapping("/users")
//    public ResponseEntity<PagedModel<EntityModel<UserResponseDto>>> getUsers(
//            @RequestParam(required = false) String filter,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "userId") String sortBy,
//            @RequestParam(defaultValue = "asc") String sortDir,
//            PagedResourcesAssembler<UserResponseDto> pagedAssembler // Must removed
//    ) {
//        Page<UserResponseDto> users = userService.getUsers(filter, page, size, sortBy, sortDir);
//        PagedModel<EntityModel<UserResponseDto>> model = pagedAssembler.toModel(users, userAssembler);
//        return ResponseEntity.ok(model);
//    }
