package com.example.demo1.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightResponseDto {
    private Long id;
    private Long flightNumber;
    private String flightName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private LocalDate travelDate;
}
