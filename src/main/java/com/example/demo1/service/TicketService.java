package com.example.demo1.service;

import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.Flight;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.FlightRepo;
import com.example.demo1.repository.TicketRepo;
import com.example.demo1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo1.constants.CommonConstants.INVALID;
import static com.example.demo1.constants.CommonConstants.NOTFOUND;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlightRepo flightRepo;

    public TicketResponseDto getTicketById(Long id) {
        Ticket ticket = ticketRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(NOTFOUND));
        return Mapper.toTicketDto(ticket);
    }


    public TicketResponseDto bookTicket(TicketRequestDto dto) {
        Flight flight = flightRepo.findById(dto.getFlightNumber())
                .orElseThrow(() -> new RuntimeException(NOTFOUND));

        UserRequestDto userRequestDto = dto.getUser();

        userRepo.findByEmailAndPassword(userRequestDto.getEmail(), userRequestDto.getPassword())
                .orElseThrow(()->new RuntimeException(INVALID));

        Optional<UserProfile> userOpt = userRepo.findByEmailAndPhone(
                dto.getUser().getEmail(), dto.getUser().getPhone());

        UserProfile user = userOpt.orElseGet(() -> userRepo.save(
                new UserProfile(null, dto.getUser().getName(),
                        dto.getUser().getGender(), dto.getUser().getPhone(), dto.getUser().getEmail(),
                         dto.getUser().getPassword(),null)));

        Ticket ticket = Mapper.toTicketEntity(dto, flight, user);

        String fare = calculateFare(dto.getTravelClass());
        ticket.setFare(fare);
        Ticket t = ticketRepo.save(ticket);
        return Mapper.toTicketDto(t);
    }

    public void deleteTicket(Long id) {
        ticketRepo.deleteById(id);
    }

    //Fare calc based on travelclass
    public String calculateFare(String travelClass) {
        if(travelClass.equalsIgnoreCase("business")){
            return "10000";
        }
        else
        {
            return "5000";
        }

    }


}
