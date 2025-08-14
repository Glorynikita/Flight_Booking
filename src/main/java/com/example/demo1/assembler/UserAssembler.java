package com.example.demo1.assembler;

import com.example.demo1.controller.UserController;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.model.UserProfile;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static com.example.demo1.constants.CommonConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {

    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withSelfRel(),
                linkTo(UserController.class).slash(USERS).withRel(ALLUSER),
                linkTo(UserController.class).slash(ADD).withRel(ADDUSER).withType(POST),
                linkTo(UserController.class).slash(UPDATE).withRel(UPUSER).withType(PUT),
                linkTo(UserController.class).slash(dto.getUserId()).withRel(DELETEUSER).withType(DELETE)
        );
    }

    public static UserResponseDto toUserDto(UserProfile userProfile) {
        return UserResponseDto.builder()
                .userId(userProfile.getUserId())
                .name(userProfile.getName())
                .gender(userProfile.getGender())
                .phone(userProfile.getPhone())
                .email(userProfile.getEmail())
                .build();
    }
}

