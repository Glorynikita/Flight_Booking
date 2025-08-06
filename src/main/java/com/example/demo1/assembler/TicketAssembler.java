package com.example.demo1.assembler;

import com.example.demo1.controller.TicketController;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TicketAssembler implements RepresentationModelAssembler<TicketResponseDto, EntityModel<TicketResponseDto>> {
    @Override
    public EntityModel<TicketResponseDto> toModel(TicketResponseDto ticket) {
        return EntityModel.of(ticket,
//                linkTo(methodOn(TicketController.class).getTicketById(ticket.getId())).withSelfRel(),
                linkTo(methodOn(TicketController.class).bookTicket(null)).withRel("book-ticket"));
//                linkTo(methodOn(FlightController.class).getAllTickets(null, null, null, 0,10,"id","asc")).withRel("all-tickets"));
    }
}
