package com.example.demo1.controller;

import com.example.demo1.assembler.RouteAssembler;
import com.example.demo1.model.Route;
import com.example.demo1.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

import static com.example.demo1.constants.CommonConstants.DELETED;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteAssembler routeAssembler;

    public  RouteController(RouteService routeService, RouteAssembler routeAssembler) {
        this.routeService = routeService;
        this.routeAssembler = routeAssembler;
    }

    @GetMapping("/all")
    public PagedModel<EntityModel<Route>> getAllRoutes(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureTime,
            @RequestParam(required = false) String arrivalTime,
            @RequestParam(required = false)LocalDate travelDate,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Route> page = routeService.getAllRoutes(source, destination, departureTime, arrivalTime, travelDate, pageNo, size, sortBy, sortDir);

        var entities = page.getContent().stream()
                .map(routeAssembler::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(entities,
                new PagedModel.PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements(), page.getTotalPages()),
                linkTo(methodOn(RouteController.class).getAllRoutes(source, destination, departureTime, arrivalTime, travelDate, pageNo, size, sortBy, sortDir)).withSelfRel());

    }

    @GetMapping("/{id}")
    public EntityModel<Route> getRouteById(@PathVariable Long id) {
        return routeAssembler.toModel(routeService.getRouteById(id));
    }

    @PostMapping("/add")
    public EntityModel<Route> addRoute(@RequestBody Route route) {
        return routeAssembler.toModel(routeService.addRoute(route));
    }

    @PutMapping("/{id}")
    public EntityModel<Route> updateRoute(@PathVariable Long id, @RequestBody Route route) {
        return routeAssembler.toModel(routeService.updateRoute(id, route));
    }

    @DeleteMapping("/{id}")
    public String deleteRoute(@PathVariable Long id) {
        routeService.deleteRoute(id);
        return DELETED;
    }
}
