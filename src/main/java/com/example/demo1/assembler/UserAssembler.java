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










































//package com.example.demo1.assembler;
//
//import com.example.demo1.controller.UserController;
//import com.example.demo1.dto.UserRequestDto;
//import com.example.demo1.dto.responseDto.UserResponseDto;
//import com.example.demo1.model.User_Profile;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {
//    public EntityModel<UserResponseDto> toModel(UserResponseDto userResponseDto) {
//        userResponseDto.add(linkTo(methodOn(UserController.class).getUserById(userResponseDto.getUserId())).withSelfRel());
//        userResponseDto.add(linkTo(methodOn(UserController.class).getUsers(null,0,10, null,null)).withRel("all-users"));
//        userResponseDto.add(linkTo(methodOn(UserController.class).addUser(null)).withRel("add-user").withType("POST"));
//        userResponseDto.add(linkTo(methodOn(UserController.class).deleteUser(null)).withRel("delete-user").withType("DELETE"));
//        return userResponseDto;
//    }
//}


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
//    @Component
//    public class UserAssembler implements RepresentationModelAssembler<UserResponseDto, EntityModel<UserResponseDto>> {
//
//        @Override
//        public EntityModel<UserResponseDto> toModel(UserResponseDto dto) {
//            return EntityModel.of(dto,
//                    linkTo(methodOn(UserController.class).getUserById(dto.getUserId())).withSelfRel(),
//                    linkTo(methodOn(UserController.class).getUsers(null, 0, 10, null, null)).withRel("all-users"),
//                    linkTo(UserController.class).slash("add").withRel("add-user").withType("POST"),
//                    linkTo(UserController.class).slash(dto.getUserId()).withRel("delete-user").withType("DELETE")
//            );
//        }
//    }
//
//
//}
