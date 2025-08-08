package com.example.demo1.service;

import com.example.demo1.Specification.RouteSpecification;
import com.example.demo1.model.Route;
import com.example.demo1.repository.RouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.example.demo1.constants.CommonConstants.ASC;
import static com.example.demo1.constants.CommonConstants.NOTFOUND;

@Service
public class RouteService {

//    field injection
//    @Autowired
//    private RouteRepo routeRepo;
//
//    @Autowired
//    private RouteSpecification routeSpecification;

    //constructor injection
    private final RouteRepo routeRepo;
    private final RouteSpecification routeSpecification;

    public  RouteService(RouteRepo routeRepo, RouteSpecification routeSpecification) {
        this.routeRepo = routeRepo;
        this.routeSpecification = routeSpecification;
    }

    public Page<Route> getAllRoutes(String source, String destination, String departureTime, String arrivalTime, LocalDate travelDate, int pageNo, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(ASC) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, size, sort);

        Specification<Route> spec = (root, query, cb) -> cb.conjunction();

        if (source != null) {
            spec = spec.and(routeSpecification.hasSource(source));
        }
        if (destination != null) {
            spec = spec.and(routeSpecification.hasDestination(destination));
        }
        if (departureTime != null) {
            spec = spec.and(routeSpecification.hasDepartureTime(departureTime));
        }
        if (arrivalTime != null) {
            spec = spec.and(routeSpecification.hasArrivalTime(arrivalTime));
        }
        if (travelDate != null) {
            spec = spec.and(routeSpecification.hasTravelDate(travelDate));
        }

        return routeRepo.findAll(spec, pageable);
    }

    public Route getRouteById(Long id) {
        return routeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(NOTFOUND));
    }

    public Route addRoute(Route route) {
        return routeRepo.save(route);
    }

    public Route updateRoute(Long id, Route route) {
        Route existing = new Route();
        existing.setSource(route.getSource());
        existing.setDestination(route.getDestination());
        existing.setDepartureTime(route.getDepartureTime());
        existing.setArrivalTime(route.getArrivalTime());
        existing.setTravelDate(route.getTravelDate());
        return routeRepo.save(existing);
    }

    public void deleteRoute(Long id) {
        routeRepo.deleteById(id);
    }
}
