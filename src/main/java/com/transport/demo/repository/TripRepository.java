package com.transport.demo.repository;

import com.transport.demo.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("""
    SELECT t FROM Trip t
    JOIN t.route r
    JOIN t.vehicle v
    WHERE (:origin IS NULL OR LOWER(r.origin) LIKE LOWER(CONCAT('%', :origin, '%')))
      AND (:destination IS NULL OR LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%')))
      AND (:vehicleType IS NULL OR LOWER(v.vehicleType) LIKE LOWER(CONCAT('%', :vehicleType, '%')))
    """)
    List<Trip> searchTrips(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("vehicleType") String vehicleType
    );

    @Query(value = """
    SELECT r.destination, COUNT(b.id) AS total
    FROM bookings b
    JOIN trips t ON t.id = b.trip_id
    JOIN routes r ON r.id = t.route_id
    GROUP BY r.destination
    ORDER BY total DESC
    """, nativeQuery = true)
    List<Object[]> findMostVisitedRaw();

    @Query(value = """
    SELECT r.destination, COUNT(b.id) AS total
    FROM bookings b
    JOIN trips t ON t.id = b.trip_id
    JOIN routes r ON r.id = t.route_id
    GROUP BY r.destination
    ORDER BY total ASC
    """, nativeQuery = true)
    List<Object[]> findLeastVisitedRaw();
}
