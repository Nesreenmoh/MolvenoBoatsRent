package com.capgemini.controllers;

import com.capgemini.models.Boat;
import com.capgemini.models.BoatType;
import com.capgemini.models.Trip;
import com.capgemini.services.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/boats")
public class BoatController {

    @Autowired
    private BoatService boatService;

    // request to get all boats
    @GetMapping
    public List<Boat> findAllBoats() {
        List<Boat> boats = boatService.getAllBoats();
        Collections.sort(boats);
        return boats;
    }

    // request to get total income of all boats
    @GetMapping("/income")
    public double getTotalIncome(){
        return boatService.getTotalIncomeOfBoats();
    }

    // request to get total income of all boats
    @GetMapping("/totalTime")
    public double getTotalTime(){
        return boatService.getTotalTimeOfBoats();
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
        List<Boat> boats = boatService.getAvailableBoat(noOfPeople,type);
        Collections.sort(boats);
        return boats;
    }

    // request to get all trips for specific boat
    @GetMapping("/trips/{boatno}")
    public List<Trip> findAllTrips(@PathVariable String boatno)
    {

        return boatService.getTripsbyBoat(boatno);
    }

    // request to get all boats by type
    @GetMapping("/type/{boatType}")
    public List<Boat> findByBoatType(@PathVariable String boatType){
        List<Boat> boats = boatService.getBoatByType(boatType);
        Collections.sort(boats);
        return boats;
    }

    // request to add a boat
    @PostMapping
    public Boat addBoat(@RequestBody Boat boat){
       return boatService.addBoat(boat);
    }


    // request for updating fields of a boat
    @PutMapping("/{boatId}")
    public void updateBoat(@RequestBody Boat boat, @PathVariable Long boatId){
        boatService.updateBoat(boat);
    }

    @DeleteMapping("/{id}")
    public String deleteBoat(@PathVariable Long id){
        boatService.deleteBoat(id);
        return "Deleted!";
    }
}
