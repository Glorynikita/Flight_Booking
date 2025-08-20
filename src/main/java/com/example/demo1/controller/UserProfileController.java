package com.example.demo1.controller;

import com.example.demo1.assembler.UserAssembler;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.model.SeatClass;
import com.example.demo1.model.UserProfile;
import com.example.demo1.service.UserProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final UserAssembler userAssembler;
    private final PagedResourcesAssembler<UserResponseDto> pagedResourcesAssembler;

    @GetMapping("/all")
    public PagedModel<EntityModel<UserResponseDto>> getUsers(
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<UserResponseDto> users = userProfileService.getUsers(filter, page, size, sortBy, sortDir);
        return pagedResourcesAssembler.toModel(users, userAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<UserResponseDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userProfileService.getUserById(id));
    }

    @PostMapping("/add")
    public EntityModel<UserResponseDto> addUser(@Valid @RequestBody UserRequestDto dto) {

        return userAssembler.toModel(userProfileService.addUser(dto));
    }

    @PutMapping("/{id}")
    public EntityModel<UserResponseDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDto dto) {
        return userAssembler.toModel(userProfileService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userProfileService.deleteUser(id));
    }

    @GetMapping("/ticket")
    public EntityModel<UserResponseDto> getUserByTicketId(@RequestParam Long ticketId) {
        return userAssembler.toModel(userProfileService.getUserByTicketId(ticketId));
    }

    @GetMapping("/by-gender/{gender}")
    public List<UserResponseDto> getUserByGender(@PathVariable String gender) {
        return userProfileService.findByGender(gender);
    }

    @GetMapping("/travel-class/{travelClass}")
    public List<UserProfile> getUserByTravelClass(@PathVariable SeatClass travelClass) {
            return userProfileService.getUserByTravelClass(travelClass);
    }

    @GetMapping("/fares/{fare}")
    public List<UserProfile> getUserByFare(@PathVariable String fare) {
        return userProfileService.getUserByFare(fare);
    }



}


