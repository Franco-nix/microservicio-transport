package com.transport.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "routes")
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String origin;
    private String destination;
    @Column(name = "estimated_hours")
    private Double estimatedHours;

    public Long getID() { return id; }
    public String getOrigin() { return origin; }
    public String getDestination() { return destination; }
}
