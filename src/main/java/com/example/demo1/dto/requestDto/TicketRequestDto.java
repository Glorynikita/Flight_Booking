package com.example.demo1.dto.requestDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketRequestDto {
    private Long flightNumber;
    private String travelClass;
    private UserRequestDto user;
    private LocalDate travelDate;
}
