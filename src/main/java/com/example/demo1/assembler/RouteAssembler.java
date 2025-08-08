package com.example.demo1.assembler;

import com.example.demo1.controller.RouteController;
import com.example.demo1.model.Route;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RouteAssembler implements RepresentationModelAssembler<Route, EntityModel<Route>> {

    @Override
    public EntityModel<Route> toModel(Route route) {
        return EntityModel.of(route,
                linkTo(methodOn(RouteController.class).getRouteById(route.getId())).withSelfRel(),
                linkTo(methodOn(RouteController.class).getAllRoutes(null, null, null, null,
                        null, 0, 10, "id", "asc")).withRel("routes"));
    }

    public PagedModel<EntityModel<Route>> toPagedModel(Page<Route> pageData, String source, String destination, String departureTime, String arrivalTime,
                                                       LocalDate travelDate, int pageNo, int size, String sortBy, String sortDir) {
        List<EntityModel<Route>> routes = pageData.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(routes,
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                ),
                linkTo(methodOn(RouteController.class).getAllRoutes(
                        source, destination, departureTime, arrivalTime, travelDate,
                        pageNo, size, sortBy, sortDir
                )).withSelfRel()
        );
    }
}
