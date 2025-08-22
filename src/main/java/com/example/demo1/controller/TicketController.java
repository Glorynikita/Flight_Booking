package com.example.demo1.controller;

import com.example.demo1.assembler.TicketAssembler;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.kafka.Producer;
import com.example.demo1.model.Ticket;
import com.example.demo1.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo1.constants.MessageConstants.BOOKED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketAssembler ticketAssembler;
    private final Producer producer;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketResponseDto>> getTicket(@PathVariable Long id){
        TicketResponseDto ticketResponseDto = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticketAssembler.toModel(ticketResponseDto));
    }

    @PostMapping("/add")
    public ResponseEntity<List<TicketResponseDto>> bookTicket(@RequestBody TicketRequestDto dto) {
        List<TicketResponseDto> response = ticketService.bookTicket(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        return ticketService.deleteTicket(id);
    }

    @GetMapping("/ticket/{flightId}")
    public List<Ticket> getTicketByFlight(@PathVariable Long flightId) {
        return ticketService.getTicketByFlightId(flightId);
    }

    @PostMapping("/book")
    public String bookTicket(@RequestParam String name, @RequestParam String flightNumber) {
        String message = "Passanger Name : " + name + "| Flight Number : " + flightNumber;
        producer.sendMessage(message);
        return BOOKED;
    }

}


