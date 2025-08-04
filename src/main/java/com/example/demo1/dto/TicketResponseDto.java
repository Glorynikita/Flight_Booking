package com.example.demo1.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TicketResponseDto {
    private Long id;
    private String name;
    private String gender;
    private Long flightNumber;
    private String flightName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private LocalDate travelDate;
    private Long seat;
    private String travelClass;

}
