package com.example.demo1.mapper;

import com.example.demo1.dto.requestDto.PassangerDto;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.requestDto.UserRequestDto;
import com.example.demo1.model.Flight;
import com.example.demo1.model.Ticket;
import com.example.demo1.model.UserProfile;
import org.springframework.stereotype.Component;

import java.util.Random;

import static com.example.demo1.constants.MessageConstants.CONFIRM;

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

    public static Ticket toTicketEntity(PassangerDto passangerDto, TicketRequestDto dto, Flight flight, UserProfile user) {
        return Ticket.builder()
                .passangerName(passangerDto.getName())
                .gender(passangerDto.getGender()) //added
                .travelClass(dto.getTravelClass())
                .seat(new Random().nextLong(1, 150))
                .source(flight.getRoute().getSource())
                .destination(flight.getRoute().getDestination())
                .status(CONFIRM)
                .flight(flight)
                .userProfile(user)
                .build();
    }

}

