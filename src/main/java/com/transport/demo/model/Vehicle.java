package com.transport.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name= "vehicles")
public class Vehicle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    @Column(name = "vehicle_type")
    private String vehicleType;
    private Integer capacity;

    public Long getId() { return id; }
    public String getPlate() { return plate; }
    public String getVehicleType() { return vehicleType; }
    public Integer getCapacity() { return capacity; }
}
