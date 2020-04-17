package com.capgemini.services;

import com.capgemini.models.Boat;
import com.capgemini.models.BoatType;
import com.capgemini.models.Trip;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;
    @Autowired
    private TripRepository tripRepository;


    // add boat
    public Boat addBoat(Boat boat) {
        if(boat.getType().equalsIgnoreCase("raft")){
            boat.setAvailable(false);
        }
        boat.setAvailable(true);
        return boatRepository.save(boat);
    }


    // retrieve all boats
    public List<Boat> getAllBoats() {

        return boatRepository.findAll();
    }

    //retrieve One boat
    public Boat getOneBoat(Long id) {
        return boatRepository.findOneById(id);
    }

    // retrieve one boat by No
    public Boat getOneBoatByNo(String no) {

        return boatRepository.findOneByNo(no);
    }


    // retrieve suitable boats
    public List<Boat> getAvailableBoat(Integer noOfPeople, String boatType) {
        List<Boat> boatList = boatRepository.findAll();
        List<Boat> foundBoats = new ArrayList<>();
        for (Boat boat : boatList) {
            if (boat.getAvailable()) {
                if ((boat.getNoOfSeats() >= noOfPeople) && ((boat.getType())).equalsIgnoreCase((boatType))) {
                    foundBoats.add(boat);
                }
            }
        }
        return foundBoats;
    }

    // retrieve trips by boat no
    public List<Trip> getTripsbyBoat(String boatNo) {
        Boat boat = boatRepository.findOneByNo(boatNo);
        List<Trip> trips = boat.getTrips();
        return trips;
    }

    //  retrieve by boat type
    public List<Boat> getBoatByType(String boatType) {
        List<Boat> myList = new ArrayList<>();
        List<Boat> allBoats = boatRepository.findAll();
        for(int i=0; i<allBoats.size();i++){
            if(allBoats.get(i).getType().equalsIgnoreCase(boatType)){
                myList.add(allBoats.get(i));
            }
        }
        return myList;
    }

    // update a boat data
    public void updateBoat(Boat boat) {
        boatRepository.save(boat);
    }

    public void updateBoatForMaintenance(Boat boat) {
        boat.setMaintenance(true);
        boat.setAvailable(false);
        boatRepository.save(boat);
    }
}
