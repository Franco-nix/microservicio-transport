package com.transport.demo.repository;

import com.transport.demo.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    Optional<Route> findByOriginIgnoreCaseAndDestinationIgnoreCase(String origin, String destination);
}
