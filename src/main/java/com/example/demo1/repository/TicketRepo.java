package com.example.demo1.repository;

import com.example.demo1.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    Long countByFlightId(Long id);


}
