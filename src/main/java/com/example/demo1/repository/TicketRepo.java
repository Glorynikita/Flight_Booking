package com.example.demo1.repository;

import com.example.demo1.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepo extends JpaRepository<Ticket, Long> {

    Long countByFlightId(Long id);

    // to fetch ticket list based on flight id
    @Query("SELECT t FROM Ticket t WHERE t.flight.id = :flightId")
    List<Ticket> findByFlightId(Long flightId);
}
