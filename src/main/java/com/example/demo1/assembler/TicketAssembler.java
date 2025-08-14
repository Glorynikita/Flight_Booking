package com.example.demo1.assembler;

import com.example.demo1.controller.TicketController;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.model.Ticket;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static com.example.demo1.constants.CommonConstants.BOOKTICKET;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TicketAssembler implements RepresentationModelAssembler<TicketResponseDto, EntityModel<TicketResponseDto>> {
    @Override
    public EntityModel<TicketResponseDto> toModel(TicketResponseDto ticket) {
        return EntityModel.of(ticket,
                linkTo(methodOn(TicketController.class).bookTicket(null)).withRel(BOOKTICKET),
                linkTo(methodOn(TicketController.class).getTicket(ticket.getId())).withSelfRel());
    }

    public static TicketResponseDto toTicketDto(Ticket ticket, Long bookedSeatCount) {
        Long availableSeats = ticket.getFlight().getTotalSeats() - bookedSeatCount;
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .name(ticket.getPassangerName())
                .gender(ticket.getUserProfile().getGender())
                .flightNumber(ticket.getFlight().getId())
                .flightName(ticket.getFlight().getFlightName())
                .source(ticket.getSource())
                .destination(ticket.getDestination())
                .departureTime(ticket.getFlight().getRoute().getDepartureTime())
                .arrivalTime(ticket.getFlight().getRoute().getArrivalTime())
                .travelDate(ticket.getFlight().getRoute().getTravelDate())
                .seat(ticket.getSeat())
                .travelClass(ticket.getTravelClass())
                .fare(ticket.getFare())
                .availableSeats(availableSeats)
                .build();
    }
}

