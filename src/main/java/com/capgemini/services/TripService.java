package com.capgemini.services;

import com.capgemini.models.Boat;
import com.capgemini.models.Guest;
import com.capgemini.models.Trip;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.GuestRepository;
import com.capgemini.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Timer;


@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private GuestRepository guestRepository;



    // add trip
    public Trip addTrip(Trip trip) {
        trip.setStartTime(LocalDateTime.now());
        tripRepository.save(trip);
        Guest guest = trip.getGuest();
        Boat boat = trip.getBoats();
        boat.setAvailable(false);
        boatRepository.save(boat);
        guestRepository.save(guest);
        return trip;
    }

    //retrieve all trips
    public List<Trip> findAllTrips() {

        return tripRepository.findAll();
    }

    //retrieve trip by id
    public Trip findOneTrip(Long id) {
        return tripRepository.findOneById(id);
    }

    // retrieve all on going trips
    public List<Trip> findAllOngoingTrips() {
        List<Trip> ongoingTripsList = new ArrayList<>();
        for (Trip myTrip : tripRepository.findAll()) {
            if (myTrip.getStatus().equalsIgnoreCase("ongoing")) {
                ongoingTripsList.add(myTrip);
            }
        }
        return ongoingTripsList;
    }

    // retrieve all ended trips
    public List<Trip> findAllEndedTrips() {
        List<Trip> endedTripList = new ArrayList<>();
        for (Trip myTrip : tripRepository.findAll()) {
            if (myTrip.getStatus().equalsIgnoreCase("ended")) {
                endedTripList.add(myTrip);
            }
        }
        return endedTripList;
    }


    // update/stop a trip
    public Trip updateTrip(Trip trip) {
        // set the end time for a trip
        trip.setEndTime(LocalDateTime.now());
        // calculate the duration of this trip
        long diff = calculateDuration(trip);
        trip.setDuration(diff);
        trip.getBoats().setTotalTime(diff);
        //  check if the boat is electrical

        if (trip.getBoats().getType().equalsIgnoreCase("electrical")) {
            if (trip.getBoats().getStatus().equalsIgnoreCase("Reserved")) {
                trip.getBoats().setAvailable(false);
                trip.getBoats().setStatus("Charging");
                updateElectricalReservedBoat(trip.getBoats());
            } else {
                trip.getBoats().setAvailable(false);
                trip.getBoats().setStatus("Charging");
                updateElectricalBoat(trip.getBoats());
            }

        } else if (trip.getBoats().getStatus().equalsIgnoreCase("Reserved")) {
            trip.getBoats().setAvailable(false);
            trip.getBoats().setStatus("Not Active");
            updateReservedBoats(trip.getBoats());

        } else {
            trip.getBoats().setAvailable(true);
        }

        // check if the boat type is raft -- the price will be the actual
        if (trip.getBoats().getType().equalsIgnoreCase("Raft")) {
            trip.getBoats().setIncome(trip.getBoats().getAccPrice());
        }
        trip.getBoats().setIncome(diff * trip.getBoats().getAccPrice());
        tripRepository.save(trip);
        boatRepository.save(trip.getBoats());
        return trip;
    }

    // calculate duration between the start time of the trip and the end time
    public long calculateDuration(Trip trip) {
        LocalDateTime date1 = trip.getEndTime();
        LocalDateTime date2 = trip.getStartTime();
        Duration period = Duration.between(date1, date2);
        long diff = Math.abs(period.toHours());
        return diff;
    }


    // retreive the income of all trips
    public double getIncomeAllTrips() {
        List<Trip> allTrips = tripRepository.findAll();
        double totalIncome = 0.0;
        for (int i = 0; i < allTrips.size(); i++) {
            if (allTrips.get(i).getBoats().getIncome() != null) {
                totalIncome += allTrips.get(i).getBoats().getIncome();
            }
        }
        return totalIncome;
    }

    //update electrical reserved boat after the trip is done
    public void updateElectricalReservedBoat(Boat boat) {
        int chargingTime = boat.getChargingTime();
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                boat.setAvailable(true);
                boat.setStatus("Active");
                boatRepository.save(boat);

            }// run this function after a charging time of the boat in millieseconds
        }, ((chargingTime  +1 ) * 3600000));
    }

    // update reserved boat after the trip
    public void updateReservedBoats(Boat boat){
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                boat.setAvailable(true);
                boat.setStatus("Active");
                boatRepository.save(boat);

            }// run this function after a charging time of the boat in millieseconds
        }, (1  * 3600000));
    }

    // update electrical boat and set the availability after the charging time
    public void updateElectricalBoat(Boat boat) {
        int chargingTime = boat.getChargingTime();
        // define a time to set a current time + charging time to change the availability to true
        Timer myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                boat.setAvailable(true);
                boat.setStatus("Active");
                boatRepository.save(boat);

            }// run this function after a charging time of the boat in millieseconds
        }, (chargingTime * 3600000));
    }


    // retrieve used boats No for all trips
    public List<Boat> getUsedBoatsForAllTrip() {
        List<Trip> trips = tripRepository.findAll();
        List<Boat> usedBoats = new ArrayList<>();
        for (int i = 0; i < trips.size(); i++) {
            boolean isUnique = false;
            for (int j = 0; j < i; j++) {
                if (trips.get(i).getBoats().getNo()
                        .equalsIgnoreCase(trips.get(j).getBoats().getNo())) {
                    isUnique = true;
                    break;
                }
            }
            if (!isUnique) {
                usedBoats.add(trips.get(i).getBoats());
            }
        }
        return usedBoats;
    }


}
