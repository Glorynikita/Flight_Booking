package com.example.demo1.repository;

import com.example.demo1.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RouteRepo extends JpaRepository<Route, Long> , JpaSpecificationExecutor<Route> {
}
