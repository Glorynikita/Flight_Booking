package com.example.demo1.service;

import com.example.demo1.Specification.FlightSpecification;
import com.example.demo1.model.Flight;
import com.example.demo1.repository.FlightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static com.example.demo1.constants.CommonConstants.ASC;
import static com.example.demo1.constants.CommonConstants.NOTFOUND;

@Service
public class FlightService {

    @Autowired
    private FlightRepo flightRepo;

    @Autowired
    private FlightSpecification flightSpecification;

    public Page<Flight> getAllflights(String flightNumber, String flightName, Long routeId, int pageNo, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(ASC) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, size, sort);

        Specification<Flight> spec = (root, query, cb) -> cb.conjunction();

        if (flightNumber != null) {
            spec= spec.and(flightSpecification.hasFlightNumber(flightNumber));
        }

        if (flightName != null) {
            spec= spec.and(flightSpecification.hasFlightName(flightName));
        }
        if (routeId != null) {
            spec = spec.and(flightSpecification.hasRouteId(routeId));
        }
        return flightRepo.findAll(spec, pageable);
    }

    public Flight getFlightById(Long id) {
        return flightRepo.findById(id)
                .orElseThrow(()-> new RuntimeException(NOTFOUND));
    }

    public Flight addFlight(Flight flight) {
        return flightRepo.save(flight);
    }

    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = new Flight();
        existing.setFlightNumber(flight.getFlightNumber());
        existing.setFlightName(flight.getFlightName());
        existing.setRoute(flight.getRoute());
        return flightRepo.save(existing);
    }

    public void deleteFlightById(Long id) {
        flightRepo.deleteById(id);
    }


}
