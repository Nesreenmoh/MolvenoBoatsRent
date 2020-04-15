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
    public void addBoat(Boat boat, BoatType checkedType) {
        boat.setType(checkedType);
        boatRepository.save(boat);
    }

//    public void linkBoats(Long id, List<String> boatsId) {
//        Trip trip = tripRepository.findOneById(id);
//        for (int i = 0; i < boatsId.size(); i++) {
//            Boat boat = boatRepository.findOneByNo(boatsId.get(i));
//            boat.setAvailable(false);
//            boat.setTrips(trip);
//            tripRepository.save(trip);
//            boatRepository.save(boat);
//        }
//        System.out.println(trip.getBoats().toString());
//    }

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
                if ((boat.getNoOfSeats() >= noOfPeople) && ((boat.getType()).toString()).equalsIgnoreCase((boatType))) {
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
    public List<Boat> getBoatByType(BoatType boatType) {

        return boatRepository.findAllByType(boatType);
    }

    // update a boat data
    public void updateBoat(Boat boat, BoatType boatType) {
        boat.setType(boatType);
        boatRepository.save(boat);
    }

    public void updateBoatForMaintenance(Boat boat, BoatType boatType) {
        boat.setType(boatType);
        boat.setMaintenance(true);
        boat.setAvailable(false);
        boatRepository.save(boat);
    }
}
