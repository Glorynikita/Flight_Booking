package com.example.demo1.assembler;

import com.example.demo1.controller.UserController;
import com.example.demo1.dto.responseDto.UserResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {

    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withSelfRel(),
                linkTo(UserController.class).slash("users").withRel("all-users"),
                linkTo(UserController.class).slash("add").withRel("add-user").withType("POST"),
                linkTo(UserController.class).slash(dto.getUserId()).withRel("delete-user").withType("DELETE")
        );
    }

}

