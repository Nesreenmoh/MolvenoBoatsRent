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

@Transactional
@Service
public class BoatService {

    @Autowired
    private BoatRepository boatRepository;
    @Autowired
    private TripRepository tripRepository;


    // add boat
    public Boat addBoat(Boat boat) {
        /* checking if a boat is raft so will not
         be available unless he has been  reservation for 4 times */
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

    // get total income for all boats
    public double getTotalIncomeOfBoats(){
        List<Boat> boats = boatRepository.findAll();
        double total=0.0;
        for(int i=0;i<boats.size();i++){
          total +=boats.get(i).getIncome();
        }
        return total;
    }

    // return total time of all boats
    public Long getTotalTimeOfBoats(){
        List<Boat> boats = boatRepository.findAll();
        Long total=0L;
        for(int i=0;i<boats.size();i++){
            total +=boats.get(i).getTotalTime();
        }
        return total;
    }

    // delete a boat

    public String deleteBoat(Long id){
        boatRepository.deleteById(id);
        return "deleted!";
    }
}
