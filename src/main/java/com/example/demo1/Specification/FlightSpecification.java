package com.example.demo1.Specification;

import com.example.demo1.model.Flight;
import com.example.demo1.model.Route;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static com.example.demo1.constants.CommonConstants.ROUTE;

@Component
public class FlightSpecification {
    public Specification<Flight> hasFlightNumber(String flightNumber){
        return (root, criteriaQuery, cb) -> flightNumber == null? null: cb.equal(root.get(Flight.Fields.flightNumber),flightNumber);
    }

    public Specification<Flight> hasFlightName(String flightName){
        return (root, query, cb) -> flightName == null ? null: cb.like(root.get(Flight.Fields.flightName),flightName);
    }

    public Specification<Flight> hasRouteId(Long routeId){
        return (root, query, criteriaBuilder) ->  routeId == null? null: criteriaBuilder.equal(root.get(ROUTE)
                .get(Route.Fields.id),routeId);
    }
}
