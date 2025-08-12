package com.example.demo1.mapper;

import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.dto.responseDto.UserResponseDto;
import com.example.demo1.model.Flight;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.UserProfile;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.example.demo1.constants.CommonConstants.CONFIRM;

@Component
public class Mapper {

    public  UserProfile toUserEntity(UserRequestDto userRequestDto) {
/*       UserProfile userProfile = new UserProfile();
        userProfile.setName(userRequestDto.getName());
        userProfile.setGender(userRequestDto.getGender());
        userProfile.setPhone(userRequestDto.getPhone());
        userProfile.setEmail(userRequestDto.getEmail());
        userProfile.setPassword(userRequestDto.getPassword());
        return userProfile; */

        return UserProfile.builder()
                .name(userRequestDto.getName())
                .gender(userRequestDto.getGender())
                .phone((userRequestDto.getPhone()))
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .build();
    }

    public static UserResponseDto toUserDto(UserProfile userProfile) {
        return UserResponseDto.builder()
                .userId(userProfile.getUserId())
                .name(userProfile.getName())
                .gender(userProfile.getGender())
                .phone(userProfile.getPhone())
                .email(userProfile.getEmail())
                .build();
    }



    public static Ticket toTicketEntity( TicketRequestDto dto, Flight flight, UserProfile user) {
        return Ticket.builder()
                .passangerName(user.getName())
                .travelClass(dto.getTravelClass())
                .seat(new Random().nextLong(1, 150))
                .source(flight.getRoute().getSource())
                .destination(flight.getRoute().getDestination())
                .status(CONFIRM)
                .flight(flight)
                .userProfile(user)
                .build();
    }




    public static TicketResponseDto toTicketDto(Ticket ticket, Long bookedSeatCount) {
        Long availableSeats = ticket.getFlight().getTotalSeats() - bookedSeatCount;
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .name(ticket.getPassangerName())
                .gender(ticket.getUserProfile().getGender())
                .flightNumber(ticket.getFlight().getId())
                .flightName(ticket.getFlight().getFlightName())
                .source(ticket.getSource())
                .destination(ticket.getDestination())
                .departureTime(ticket.getFlight().getRoute().getDepartureTime())
                .arrivalTime(ticket.getFlight().getRoute().getArrivalTime())
                .travelDate(ticket.getFlight().getRoute().getTravelDate())
                .seat(ticket.getSeat())
                .travelClass(ticket.getTravelClass())
                .fare(ticket.getFare())
                .availableSeats(availableSeats)
                .build();
    }

}

