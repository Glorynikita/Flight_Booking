package com.example.demo1.dto;

import lombok.Data;

@Data
public class TicketRequestDto {
    private Long flightNumber;
    private String travelClass;
    private UserRequestDto user;
}
