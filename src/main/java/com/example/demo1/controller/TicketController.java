package com.example.demo1.controller;

import com.example.demo1.assembler.TicketAssembler;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.kafka.Producer;
import com.example.demo1.model.Ticket;
import com.example.demo1.service.TicketService;
import com.example.demo1.translator.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketAssembler ticketAssembler;
    private final Producer producer;
    private final Translator translator;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketResponseDto>> getTicket(@PathVariable Long id, Locale locale) {
        TicketResponseDto ticketResponseDto = ticketService.getTicketById(id, locale);
        return ResponseEntity.ok(ticketAssembler.toModel(ticketResponseDto));
    }

    @PostMapping("/add")
    public ResponseEntity<List<TicketResponseDto>> bookTicket(@RequestBody TicketRequestDto dto, Locale locale) {
        List<TicketResponseDto> response = ticketService.bookTicket(dto,locale);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id, Locale locale) {
        return ticketService.deleteTicket(id,locale);
    }

    @GetMapping("/ticket/{flightId}")
    public List<Ticket> getTicketByFlight(@PathVariable Long flightId) {
        return ticketService.getTicketByFlightId(flightId);
    }

    @PostMapping("/book")
    public String bookTicket(@RequestParam String name, @RequestParam String flightNumber,Locale locale) {
        String message = "Passanger Name : " + name + "| Flight Number : " + flightNumber;
        producer.sendMessage(message);
        return translator.toLocale("booked", locale);
    }

}


