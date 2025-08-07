package com.example.demo1.mapper;

import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.model.Flight;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Mapper {

    @Autowired
    private FlightRepo flightRepo;

    public  UserProfile toUserEntity(UserRequestDto userRequestDto) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userRequestDto.getName());
        userProfile.setGender(userRequestDto.getGender());
        userProfile.setPhone(userRequestDto.getPhone());
        userProfile.setEmail(userRequestDto.getEmail());
        userProfile.setPassword(userRequestDto.getPassword());
        return userProfile;
    }

    public static UserResponseDto toUserDto(UserProfile userProfile) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(userProfile.getUserId());
        userResponseDto.setName(userProfile.getName());
        userResponseDto.setGender(userProfile.getGender());
        userResponseDto.setPhone(userProfile.getPhone());
        userResponseDto.setEmail(userProfile.getEmail());
        return userResponseDto;
    }

        public static Ticket toTicketEntity(TicketRequestDto dto, Flight flight, UserProfile user) {
            Ticket ticket = new Ticket();
            ticket.setPassangerName(user.getName());
            ticket.setTravelClass(dto.getTravelClass());
            ticket.setSeat(new Random().nextLong(1, 150));
            ticket.setSource(flight.getRoute().getSource());
            ticket.setDestination(flight.getRoute().getDestination());
            ticket.setStatus("CONFIRMED");
            ticket.setFlight(flight);
            ticket.setUserProfile(user);
            return ticket;
        }

        public static TicketResponseDto toTicketDto(Ticket ticket) {
            TicketResponseDto dto = new TicketResponseDto();
            dto.setId(ticket.getId());
            dto.setName(ticket.getUserProfile().getName());
            dto.setGender(ticket.getUserProfile().getGender());
            dto.setFlightNumber(ticket.getFlight().getId());
            dto.setFlightName(ticket.getFlight().getFlightName());
            dto.setSource(ticket.getSource());
            dto.setDestination(ticket.getDestination());
            dto.setDepartureTime(ticket.getFlight().getRoute().getDepartureTime());
            dto.setArrivalTime(ticket.getFlight().getRoute().getArrivalTime());
            dto.setTravelDate(ticket.getFlight().getRoute().getTravelDate());
            dto.setSeat(ticket.getSeat());
            dto.setTravelClass(ticket.getTravelClass());
            dto.setFare(ticket.getFare());
            return dto;
        }

//    private Long generateRandomSeat() {
//        return (long) ((Math.random() * 100) + 1);
//    }
}

