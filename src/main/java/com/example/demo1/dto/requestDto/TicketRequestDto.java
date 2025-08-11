package com.example.demo1.dto.requestDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TicketRequestDto {
    private Long flightNumber;
    private String travelClass;
    private UserRequestDto user;
    private LocalDate travelDate;
}
