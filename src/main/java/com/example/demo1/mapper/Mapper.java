package com.example.demo1.mapper;

import com.example.demo1.dto.FlightResponseDto;
import com.example.demo1.dto.TicketResponseDto;
import com.example.demo1.dto.UserRequestDto;
import com.example.demo1.dto.UserResponseDto;
import com.example.demo1.model.Flight;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.User_Profile;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public static User_Profile toUserEntity(UserRequestDto userRequestDto){
        User_Profile userProfile = new User_Profile();
        userProfile.setName(userRequestDto.getName());
        userProfile.setGender(userRequestDto.getGender());
        userProfile.setPhone(userRequestDto.getPhone());
        userProfile.setEmail(userRequestDto.getEmail());
        userProfile.setPassword(userRequestDto.getPassword());
        return userProfile;
    }

    public static UserResponseDto toUserDto(User_Profile userProfile){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setUserId(userProfile.getUserId());
        userResponseDto.setName(userProfile.getName());
        userResponseDto.setGender(userProfile.getGender());
        userResponseDto.setPhone(userProfile.getPhone());
        userResponseDto.setEmail(userProfile.getEmail());
        return userResponseDto;
    }

    public static TicketResponseDto toTicketDto(Ticket ticket){
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setId(ticket.getId());
        ticketResponseDto.setName(ticket.getUserProfile().getName());
        ticketResponseDto.setGender(ticket.getUserProfile().getGender());
        ticketResponseDto.setFlightNumber(ticket.getFlight().getFlightNumber());
        ticketResponseDto.setFlightName(ticket.getFlight().getFlightName());
        ticketResponseDto.setSource(ticket.getFlight().getRoute().getSource());
        ticketResponseDto.setDestination(ticket.getFlight().getRoute().getDestination());
        ticketResponseDto.setArrivalTime(ticket.getFlight().getRoute().getArrivalTime());
        ticketResponseDto.setDepartureTime(ticket.getFlight().getRoute().getDepartureTime());
        ticketResponseDto.setTravelDate(ticket.getFlight().getRoute().getTravelDate());
        ticketResponseDto.setSeat(ticket.getSeat());
        ticketResponseDto.setTravelClass(ticket.getTravelClass());
        return ticketResponseDto;
    }

    public static FlightResponseDto toFlightDto(Flight flight){
        FlightResponseDto flightResponseDto = new FlightResponseDto();
        flightResponseDto.setId(flight.getId());
        flightResponseDto.setFlightNumber(flight.getFlightNumber());
        flightResponseDto.setFlightName(flight.getFlightName());
        flightResponseDto.setSource(flight.getRoute().getSource());
        flightResponseDto.setDestination(flight.getRoute().getDestination());
        flightResponseDto.setArrivalTime(flight.getRoute().getArrivalTime());
        flightResponseDto.setDepartureTime(flight.getRoute().getDepartureTime());
        flightResponseDto.setTravelDate(flight.getRoute().getTravelDate());
        return flightResponseDto;
    }
}
