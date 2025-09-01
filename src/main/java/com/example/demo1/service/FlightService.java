package com.example.demo1.service;

import com.example.demo1.Specification.FlightSpecification;
import com.example.demo1.model.Flight;
import com.example.demo1.repository.FlightRepo;
import com.example.demo1.translator.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.example.demo1.constants.CommonConstants.ASC;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepo flightRepo;
    private final FlightSpecification flightSpecification;
    private final Translator translator;

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

    public Flight getFlightById(Long id, Locale locale) {
        return flightRepo.findById(id)
                .orElseThrow(()-> new RuntimeException(translator.toLocale("flight.not.found", locale)));
    }

    public Flight addFlight(Flight flight) {
        return flightRepo.save(flight);
    }

    public Flight updateFlight(Long id, Flight flight, Locale locale) {
        Flight existing =flightRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(translator.toLocale("flight.not.found", locale)));
        existing.setFlightNumber(flight.getFlightNumber());
        existing.setFlightName(flight.getFlightName());
        existing.setRoute(flight.getRoute());
        return flightRepo.save(existing);
    }

    public String deleteFlightById(Long id, Locale locale) {
        if (!flightRepo.existsById(id)) {
            throw new RuntimeException(translator.toLocale("flight.not.found", locale));
        }
        else {
            flightRepo.deleteById(id);
            return translator.toLocale("deleted", locale);
        }
    }
}
