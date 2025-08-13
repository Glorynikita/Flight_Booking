package com.example.demo1.dto.requestDto;

import com.example.demo1.model.SeatClass;
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
    private SeatClass travelClass;
    private UserRequestDto user;
    private LocalDate travelDate;
    private List<PassangerDto> passengers;
}
