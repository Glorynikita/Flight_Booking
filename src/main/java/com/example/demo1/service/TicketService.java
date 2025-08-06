package com.example.demo1.service;

import com.example.demo1.assembler.FlightAssembler;
import com.example.demo1.assembler.TicketAssembler;
import com.example.demo1.model.Ticket;
import com.example.demo1.dto.requestDto.TicketRequestDto;
import com.example.demo1.dto.responseDto.TicketResponseDto;
import com.example.demo1.mapper.Mapper;
import com.example.demo1.model.Flight;
import com.example.demo1.model.UserProfile;
import com.example.demo1.repository.FlightRepo;
import com.example.demo1.repository.TicketRepo;
import com.example.demo1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.demo1.constants.CommonConstants.NOTFOUND;

@Service
public class TicketService {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlightRepo flightRepo;
    @Autowired
    private FlightAssembler flightAssembler;
    @Autowired
    private TicketAssembler ticketAssembler;

//    @Autowired
//    private Mapper mapper;



    public TicketResponseDto bookTicket(TicketRequestDto dto) {
        Flight flight = flightRepo.findById(dto.getFlightNumber())
                .orElseThrow(() -> new RuntimeException(NOTFOUND));

        Optional<UserProfile> userOpt = userRepo.findByEmailAndPhone(
                dto.getUser().getEmail(), dto.getUser().getPhone());

        UserProfile user = userOpt.orElseGet(() -> userRepo.save(
                new UserProfile(null, dto.getUser().getName(),
                        dto.getUser().getGender(), dto.getUser().getEmail(),
                        dto.getUser().getPhone(), dto.getUser().getPassword())));

        Ticket ticket = Mapper.toTicketEntity(dto, flight, user);
        return Mapper.toTicketDto(ticketRepo.save(ticket));
    }



















//    public PagedModel<EntityModel<TicketResponseDto>> getAllTickets(int pageNo, int pageSize, String sortBy, String sortDir) {
//        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
//
//        Page<Ticket> page = ticketRepo.findAll(pageable);
//        Page<TicketResponseDto> dtoPage = page.map(Mapper::toTicketDto);
//
//        return flightAssembler.toModel(dtoPage, assembler);
//    }


}
