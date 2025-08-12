package com.example.demo1.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketRequestDto {
    private Long flightNumber;
    private String travelClass;
    private UserRequestDto user;
    private LocalDate travelDate;
}
