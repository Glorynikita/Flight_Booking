package com.example.demo1.assembler;

import com.example.demo1.controller.FlightController;
import com.example.demo1.model.Flight;
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
public class FlightAssembler implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {

    @Override
    public EntityModel<Flight> toModel(Flight flight) {
        return EntityModel.of(flight,
                linkTo(methodOn(FlightController.class).getFlightById(flight.getId())).withSelfRel(),
                linkTo(methodOn(FlightController.class).getAllFlights(null, null, null, 0, 10, "id", "asc"))
                        .withRel("all-flights"));
    }

    public PagedModel<EntityModel<Flight>> toPagedModel(Page<Flight> pageData, String flightNumber, String flightName, Long routeId, int pageNo, int size, String sortBy,
                                                        String sortDir) {
        List<EntityModel<Flight>> flights = pageData.getContent().stream()
                .map(this::toModel)
                .collect(Collectors.toList());

        return PagedModel.of(flights,
                new PagedModel.PageMetadata(
                        pageData.getSize(),
                        pageData.getNumber(),
                        pageData.getTotalElements(),
                        pageData.getTotalPages()
                ),
                linkTo(methodOn(FlightController.class).getAllFlights(
                        flightNumber, flightName, routeId,
                        pageNo, size, sortBy, sortDir))
                        .withSelfRel()
        );
    }
}

