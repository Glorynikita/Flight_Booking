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

@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketAssembler ticketAssembler;

    @PostMapping("/add")
    public ResponseEntity<EntityModel<TicketResponseDto>> bookTicket(@RequestBody TicketRequestDto dto) {
        TicketResponseDto saved = ticketService.bookTicket(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ticketAssembler.toModel(saved));
    }


}


