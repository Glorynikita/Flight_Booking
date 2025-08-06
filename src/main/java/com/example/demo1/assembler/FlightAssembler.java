package com.example.demo1.assembler;

import com.example.demo1.controller.FlightController;
import com.example.demo1.model.Flight;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class FlightAssembler implements RepresentationModelAssembler<Flight, EntityModel<Flight>> {
    @Override
    public EntityModel<Flight> toModel(Flight flight) {
        return EntityModel.of(flight,
                linkTo(methodOn(FlightController.class).getFlightById(flight.getId())).withSelfRel(),
                linkTo(methodOn(FlightController.class).getAllFlights(null, null, null, 0,10,"id","asc")).withRel("all-flights"));
    }

}
