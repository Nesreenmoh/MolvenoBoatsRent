package com.capgemini.controllers;

import com.capgemini.models.Boat;
import com.capgemini.models.Trip;
import com.capgemini.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    // request to get all trips
    @GetMapping
    public List<Trip> getAllTrips() {
        return tripService.findAllTrips();
    }

    // request to get one trip by id
    @GetMapping("/{id}")
    public Trip getOneTrip(@PathVariable Long id) {
        return tripService.findOneTrip(id);
    }

//    // request to get all boats of specific trip
//    @GetMapping("/boats/{id}")
//    public String getAllBoatsOfTrip(@PathVariable Long id){
//        return tripService.findAllboats(id);
//    }

    // request to get all ended trips
    @GetMapping("/ended")
    public List<Trip> getEndedTrips(){
        return tripService.findAllEndedTrips();
    }

    // request to get all ongoing trips
    @GetMapping("/ongoing")
    public List<Trip> getOngoingTrips(){
        return tripService.findAllOngoingTrips();
    }

    // request to get the income of all trips
    @GetMapping("/totalincome")
    public double getIncomeAllTrips(){
        return tripService.getIncomeAllTrips();
    }
    // request to add a trip
    @PostMapping
    public void addTrip(@RequestBody Trip trip) {
        tripService.addTrip(trip);
    }

    // request to update a trip
    @PutMapping("/updatetrip/{id}")
    public Trip stopTrip(@RequestBody Trip trip,@PathVariable Long id){
       return tripService.updateTrip(trip);
    }
}
