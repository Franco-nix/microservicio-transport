package com.transport.demo.controller;

import com.transport.demo.dto.TripRequest;
import com.transport.demo.model.Trip;
import com.transport.demo.service.TripService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransportController {

    private final TripService tripService;

    public TransportController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping("/")
    public String searchTrips(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String vehicleType,
            Model model) {

        List<Trip> trips = tripService.searchTrips(origin, destination, vehicleType);

        model.addAttribute("trips", trips);
        model.addAttribute("origin", origin);
        model.addAttribute("destination", destination);
        model.addAttribute("vehicleType", vehicleType);

        return "index";
    }

    @ResponseBody
    @GetMapping("/api/trips")
    public List<Trip> searchTripsApi(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String vehicleType) {
        return tripService.searchTrips(origin, destination, vehicleType);
    }

    @ResponseBody
    @GetMapping("/api/stats/destinations/most-visited")
    public List<Object[]> getMostVisitedApi() {
        return tripService.findMostVisitedRaw();
    }

    @ResponseBody
    @GetMapping("/api/stats/destinations/least-visited")
    public List<Object[]> getLeastVisitedApi() {
        return tripService.findLeastVisitedRaw();
    }

    @PostMapping("/api/trips")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Trip createTrip(@RequestBody TripRequest request) {
        return tripService.createTrip(request);
    }
}
