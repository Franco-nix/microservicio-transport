package com.transport.demo.dto;

public class DestinationStat {
    private String destination;
    private Long totalBookings;

    public DestinationStat(String destination, Long totalBookings) {
        this.destination = destination;
        this.totalBookings = totalBookings;
    }
    public String getDestination() { return destination;}
    public Long gesTotalBookings() { return totalBookings; }
}
