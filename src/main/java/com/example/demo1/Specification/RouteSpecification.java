package com.example.demo1.Specification;

import com.example.demo1.model.Route;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RouteSpecification {
    public Specification<Route> hasSource(String source){
        return (root, criteriaQuery, cb) -> source==null ? null : cb.equal(root.get(Route.Fields.source),source);
    }

    public Specification<Route> hasDestination(String destination){
        return (root, query, cb) ->  destination==null ? null : cb.equal(root.get(Route.Fields.destination),destination);
    }

    public Specification<Route> hasDepartureTime(String departureTime){
        return (root, query, cb) -> departureTime==null ? null : cb.equal(root.get(Route.Fields.departureTime),departureTime);
    }

    public Specification<Route> hasArrivalTime(String arrivalTime){
        return (root, query, cb) -> arrivalTime==null ? null : cb.equal(root.get(Route.Fields.arrivalTime),arrivalTime);
    }

    public Specification<Route> hasTravelDate(LocalDate travelDate){
        return (root, query, cb) ->  travelDate==null ? null : cb.equal(root.get(Route.Fields.travelDate),travelDate);
    }
}
