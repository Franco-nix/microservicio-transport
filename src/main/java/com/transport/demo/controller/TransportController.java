package com.transport.demo.controller;

import com.transport.demo.model.Trip;
import com.transport.demo.repository.TripRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TransportController {

    private final TripRepository tripRepository;

    public TransportController(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    @GetMapping("/")
    public String searchTrips(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) String vehicleType,
            Model model) {

        List<Trip> trips = tripRepository.searchTrips(origin, destination, vehicleType);

        model.addAttribute("trips", trips);
        model.addAttribute("origin", origin);
        model.addAttribute("destination", destination);
        model.addAttribute("vehicleType", vehicleType);

        return "index";
    }

    @ResponseBody
    @GetMapping("/api/stats/destinations/most-visited")
    public List<Object[]> getMostVisitedApi() {
        return tripRepository.findMostVisitedRaw();
    }

    @ResponseBody
    @GetMapping("/api/stats/destinations/least-visited")
    public List<Object[]> getLeastVisitedApi() {
        return tripRepository.findLeastVisitedRaw();
    }
}