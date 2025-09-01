package com.example.demo1.assembler;

import com.example.demo1.controller.RouteController;
import com.example.demo1.model.Route;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static com.example.demo1.constants.CommonConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RouteAssembler implements RepresentationModelAssembler<Route, EntityModel<Route>> {

    @Override
    public EntityModel<Route> toModel(Route route) {
        return EntityModel.of(route,
                linkTo(methodOn(RouteController.class).getRouteById(route.getId(), null)).withSelfRel(),
                linkTo(methodOn(RouteController.class)
                        .getAllRoutes(null, null, null, null,
                        null, 0, 10, ID, ASC)).withRel(ROUTES).expand());
    }

}
