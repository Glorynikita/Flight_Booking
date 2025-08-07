package com.example.demo1.controller;

import com.example.demo1.assembler.TicketAssembler;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.demo1.constants.CommonConstants.DELETED;

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketAssembler ticketAssembler;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketResponseDto>> getTicket(@PathVariable Long id) {
        TicketResponseDto ticketResponseDto = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticketAssembler.toModel(ticketResponseDto));
    }

    @PostMapping("/add")
    public ResponseEntity<TicketResponseDto> bookTicket(@RequestBody TicketRequestDto dto) {
        TicketResponseDto response = ticketService.bookTicket(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public String deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return DELETED;
    }


}


