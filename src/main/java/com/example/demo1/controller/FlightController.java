package com.example.demo1.controller;

import com.example.demo1.assembler.FlightAssembler;
import com.example.demo1.model.Flight;
import com.example.demo1.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/flights")
public class FlightController {

    private final FlightService flightService;
    private final FlightAssembler flightAssembler;
    private final PagedResourcesAssembler<Flight> pagedResourcesAssembler;


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
        return pagedResourcesAssembler.toModel(pages, flightAssembler);
    }

    @GetMapping("{id}")
    public EntityModel<Flight> getFlightById(@PathVariable Long id , Locale locale) {
        return flightAssembler.toModel(flightService.getFlightById(id,locale));
    }

    @PostMapping("/add")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/{id}")
    public EntityModel<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight, Locale locale) {
        return flightAssembler.toModel(flightService.updateFlight(id, flight, locale));
    }

    @DeleteMapping("/{id}")
    public String deleteFlightById(@PathVariable Long id, Locale locale) {
        return flightService.deleteFlightById(id, locale);

    }
}
