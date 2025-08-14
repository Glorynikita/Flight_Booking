package com.example.demo1.controller;

import com.example.demo1.assembler.RouteAssembler;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.model.Route;
import com.example.demo1.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;
    private final RouteAssembler routeAssembler;
    private final PagedResourcesAssembler<Route> pagedResourcesAssembler;

    @GetMapping("/all")
    public PagedModel<EntityModel<Route>> getAllRoutes(
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String departureTime,
            @RequestParam(required = false) String arrivalTime,
            @RequestParam(required = false) LocalDate travelDate,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Route> page = routeService.getAllRoutes(source, destination, departureTime, arrivalTime, travelDate, pageNo, size, sortBy, sortDir);
        return pagedResourcesAssembler.toModel(page, routeAssembler);
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
        return routeService.deleteRoute(id);
    }
}
