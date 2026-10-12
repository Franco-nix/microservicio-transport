package com.transport.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
public class Trip {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @Column(name = "departure_date")
    private LocalDateTime departureDate;

    private Double price;
    private String status;

    public Long getId() { return id; }
    public Vehicle getVehicle() { return vehicle; }
    public Route getRoute() { return route; }
    public LocalDateTime getDepartureDate() { return departureDate; }
    public Double getPrice() { return price; }
    public String getStatus() { return status; }
}
