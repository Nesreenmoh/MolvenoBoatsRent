package com.capgemini.controllers;

import com.capgemini.models.Boat;
import com.capgemini.models.BoatType;
import com.capgemini.models.Trip;
import com.capgemini.services.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    // request to get all boats
    @GetMapping
    public List<Boat> findAllBoats() {

        return boatService.getAllBoats();
    }

    //request to get a boat by id
    @GetMapping("/{boatId}")
    public Boat findOneBoatById(@PathVariable Long boatId) {

        return boatService.getOneBoat(boatId);
    }

    // request to get a boat by its number
    @GetMapping("/boat/{boatNo}")
    public Boat findOneBoatByNo(@PathVariable String boatNo) {

        return boatService.getOneBoatByNo(boatNo);
    }

    // request to check for suitable boat
    @GetMapping("/{noOfPeople}/{type}")
    public List<Boat> checkBoats(@PathVariable Integer noOfPeople,@PathVariable String type ){
        return boatService.getAvailableBoat(noOfPeople,type);
    }

    // request to get all trips for specific boat
    @GetMapping("/trips/{boatno}")
    public List<Trip> findAllTrips(@PathVariable String boatno)
    {

        return boatService.getTripsbyBoat(boatno);
    }

    // request to get all boats by type
    @GetMapping("/boattype/{boatType}")
    public List<Boat> findByBoatType(@PathVariable String boatType){
        return boatService.getBoatByType(boatType);
    }

    // request to add a boat
    @PostMapping
    public void addBoat(@RequestBody Boat boat){

        boatService.addBoat(boat);
    }


    // request for updating fields of a boat
    @PutMapping("/{boatId}")
    public void updateBoat(@RequestBody Boat boat, @PathVariable Long boatId){
        boatService.updateBoat(boat);
    }

}
