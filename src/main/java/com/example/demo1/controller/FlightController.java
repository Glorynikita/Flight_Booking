package com.example.demo1.controller;

import com.example.demo1.assembler.FlightAssembler;
import com.example.demo1.model.Flight;
import com.example.demo1.service.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import static com.example.demo1.constants.CommonConstants.DELETED;

@RestController
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightAssembler flightAssembler;

    public  FlightController(FlightService flightService, FlightAssembler flightAssembler) {
        this.flightService = flightService;
        this.flightAssembler = flightAssembler;
    }

    @GetMapping("/all")
    public PagedModel<EntityModel<Flight>> getAllFlights(
            @RequestParam(required = false) String flightNumber,
            @RequestParam(required = false) String flightName,
            @RequestParam(required = false) Long routeId,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<Flight> pages = flightService.getAllflights(flightNumber, flightName, routeId, pageNo, size, sortBy, sortDir);
        return flightAssembler.toPagedModel(pages, flightNumber, flightName, routeId, pageNo, size, sortBy, sortDir);
    }

    @GetMapping("{id}")
    public EntityModel<Flight> getFlightById(@PathVariable Long id) {
        return flightAssembler.toModel(flightService.getFlightById(id));
    }

    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/{id}")
    public EntityModel<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightAssembler.toModel(flightService.updateFlight(id, flight));
    }

    @DeleteMapping("/{id}")
    public String deleteFlightById(@PathVariable Long id) {
        flightService.deleteFlightById(id);
        return DELETED;
    }
}
