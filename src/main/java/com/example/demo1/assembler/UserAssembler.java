package com.example.demo1.assembler;

import com.example.demo1.controller.UserController;
import com.example.demo1.dto.responseDto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {

    @Override
    public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withSelfRel(),
                linkTo(UserController.class).slash("users").withRel("all-users"),
                linkTo(UserController.class).slash("add").withRel("add-user").withType("POST"),
                linkTo(UserController.class).slash("update").withRel("update-user").withType("PUT"),
                linkTo(UserController.class).slash(dto.getUserId()).withRel("delete-user").withType("DELETE")
        );
    }

    public PagedModel<EntityModel<UserResponseDto>> toPagedModel(Page<UserResponseDto> pageData, String filter, int page, int size, String sortBy, String sortDir) {
        List<EntityModel<UserResponseDto>> users = pageData.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(users,
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                ),
                linkTo(methodOn(UserController.class)
                        .getUsers(filter, page, size, sortBy, sortDir))
                        .withSelfRel()
        );
    }
}

































//package com.example.demo1.assembler;
//
//import com.example.demo1.controller.UserController;
//import com.example.demo1.dto.responseDto.UserResponseDto;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//
//@Component
//public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {
//
//    @Override
//    public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
//        return EntityModel.of(dto,
//                linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withSelfRel(),
//                linkTo(UserController.class).slash("users").withRel("all-users"),
//                linkTo(UserController.class).slash("add").withRel("add-user").withType("POST"),
//                linkTo(UserController.class).slash(dto.getUserId()).withRel("delete-user").withType("DELETE")
//        );
//    }
//
//}

