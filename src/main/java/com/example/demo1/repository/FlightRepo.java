package com.example.demo1.repository;

import com.example.demo1.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FlightRepo extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {
}
