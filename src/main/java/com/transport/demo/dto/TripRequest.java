package com.transport.demo.dto;

import java.time.LocalDateTime;

public record TripRequest(
        String origin,
        String destination,
        LocalDateTime departureDate,
        Double price,
        String status,
        String plate,
        String vehicleType,
        Integer capacity
) {
}
