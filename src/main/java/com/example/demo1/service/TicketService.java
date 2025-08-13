package com.example.demo1.service;

import com.example.demo1.dto.requestDto.PassangerDto;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.exception.InvalidUserException;
import com.example.demo1.exception.TicketNotFound;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.Flight;
import com.example.demo1.model.SeatClass;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.FlightRepo;
import com.example.demo1.repository.TicketRepo;
import com.example.demo1.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo1.constants.CommonConstants.*;
import static com.example.demo1.constants.MessageConstants.*;

@Slf4j
@Service
public class TicketService {

    private final TicketRepo ticketRepo;
    private final UserRepo userRepo;
    private final FlightRepo flightRepo;

    public TicketService(TicketRepo ticketRepo, UserRepo userRepo, FlightRepo flightRepo) {
        this.ticketRepo = ticketRepo;
        this.userRepo = userRepo;
        this.flightRepo = flightRepo;
    }

    public TicketResponseDto getTicketById(Long id) {
        log.info("Ticket Details");
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(()->new TicketNotFound(NOTFOUND));

        Long bookedSeatCount = ticketRepo.countByFlightId(ticket.getFlight().getId());
        return Mapper.toTicketDto(ticket,  bookedSeatCount);
    }



    public List<TicketResponseDto> bookTicket(TicketRequestDto dto) {
        Flight flight = flightRepo.findById(dto.getFlightNumber())
                .orElseThrow(() -> new RuntimeException(NOTFOUND));

        UserRequestDto userRequestDto = dto.getUser();

        // Login validation
        userRepo.findByEmailAndPassword(userRequestDto.getEmail(), userRequestDto.getPassword())
                .orElseThrow(() -> new InvalidUserException(INVALID));

        // Duplicate sign in check
        Optional<UserProfile> userOpt = userRepo.findByEmailAndPhone(
                dto.getUser().getEmail(), dto.getUser().getPhone());

        UserProfile user = userOpt.orElseGet(() -> userRepo.save(
                new UserProfile(null, dto.getUser().getName(),
                        dto.getUser().getGender(), dto.getUser().getPhone(), dto.getUser().getEmail(),
                        dto.getUser().getPassword(), null)));

        Long bookedSeatCount = ticketRepo.countByFlightId(flight.getId());

        List<TicketResponseDto> bookedTickets = new ArrayList<>();

        for (PassangerDto passenger : dto.getPassengers()) {
            Ticket ticket = Mapper.toTicketEntity(passenger, dto, flight, user);
            String fare = calculateFare(dto.getTravelClass());
            ticket.setFare(fare);

            Ticket savedTicket = ticketRepo.save(ticket);
            bookedTickets.add(Mapper.toTicketDto(savedTicket, bookedSeatCount + bookedTickets.size()));
        }

        return bookedTickets;
    }

    public String deleteTicket(Long id) {
        if (!ticketRepo.existsById(id)) {
            throw new RuntimeException(NOTFOUND);
        }
        else {
            ticketRepo.deleteById(id);
            return CANCEL;
        }

    }

    //Fare calc based on travelClass
    public String calculateFare(SeatClass travelClass) {
        switch (travelClass) {
            case BUSINESS:
                return "10000";
            case ECONOMY:
                return "5000";
            default:
                throw new IllegalStateException("Unexpected value: " + travelClass);
        }
    }

}
