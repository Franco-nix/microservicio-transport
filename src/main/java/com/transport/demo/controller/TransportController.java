package com.transport.demo.controller;

import com.transport.demo.dto.TripRequest;
import com.transport.demo.model.Route;
import com.transport.demo.model.Trip;
import com.transport.demo.model.Vehicle;
import com.transport.demo.repository.RouteRepository;
import com.transport.demo.repository.TripRepository;
import com.transport.demo.repository.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
public class TransportController {

    private final TripRepository tripRepository;
    private final RouteRepository routeRepository;
    private final VehicleRepository vehicleRepository;

    public TransportController(TripRepository tripRepository,
                               RouteRepository routeRepository,
                               VehicleRepository vehicleRepository) {
        this.tripRepository = tripRepository;
        this.routeRepository = routeRepository;
        this.vehicleRepository = vehicleRepository;
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

    @PostMapping("/api/trips")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Trip createTrip(@RequestBody TripRequest request) {
        if (request.origin() == null || request.origin().isBlank()
                || request.destination() == null || request.destination().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "origin y destination son obligatorios");
        }
        if (request.departureDate() == null || request.price() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "departureDate y price son obligatorios");
        }

        String origin = request.origin().trim();
        String destination = request.destination().trim();

        Route route = routeRepository.findByOriginIgnoreCaseAndDestinationIgnoreCase(origin, destination)
                .orElseGet(() -> {
                    Route newRoute = new Route();
                    newRoute.setOrigin(origin);
                    newRoute.setDestination(destination);
                    return routeRepository.save(newRoute);
                });

        Vehicle vehicle = null;
        if (request.plate() != null && !request.plate().isBlank()) {
            String plate = request.plate().trim();
            vehicle = vehicleRepository.findByPlateIgnoreCase(plate)
                    .orElseGet(() -> {
                        Vehicle newVehicle = new Vehicle();
                        newVehicle.setPlate(plate);
                        newVehicle.setVehicleType(request.vehicleType());
                        newVehicle.setCapacity(request.capacity());
                        return vehicleRepository.save(newVehicle);
                    });
        }

        Trip trip = new Trip();
        trip.setRoute(route);
        trip.setVehicle(vehicle);
        trip.setDepartureDate(request.departureDate());
        trip.setPrice(request.price());
        trip.setStatus(request.status() != null && !request.status().isBlank()
                ? request.status()
                : "SCHEDULED");

        return tripRepository.save(trip);
    }
}
