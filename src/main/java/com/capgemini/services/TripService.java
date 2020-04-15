package com.capgemini.services;

import com.capgemini.models.Boat;
import com.capgemini.models.Guest;
import com.capgemini.models.Trip;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Transactional
public class TripService {

    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private BoatRepository boatRepository;

    Boat boat;

    // add trip
    public void addTrip(Trip trip) {
        trip.setStartTime(LocalDateTime.now());
        tripRepository.save(trip);
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

//    public String findAllboats(Long id){
//        Trip trip=tripRepository.findOneById(id);
//        System.out.println("my trip" +trip.getBoats().toString());
//        return trip.getBoats().toString();
//    }
    // retrieve all boats of specific trip
//    public List<Boat> findAllBoats(Long id) {
//        Trip trip = tripRepository.findOneById(id);
//        //System.out.println(trip.getBoats().toString());
//        return trip.getBoats();
//    }

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


    // update a trip
    public Trip updateTrip(Trip trip) {
        // set the end time for a trip
        Boolean state;
         if(trip.getBoats().getType().equalsIgnoreCase("electrical")){
             state= false;
             updateElectricalBoat(trip.getBoats());
         }
         else {
             state=true;
         }
        System.out.println("state is "+ state);
        trip.setEndTime(LocalDateTime.now());
        // calculate the duration of this trip
        long diff = calculateDuration(trip);
        trip.setDuration(diff);
        trip.getBoats().setAvailable(state);
        trip.getBoats().setIncome(diff  * trip.getBoats().getAccPrice());
        trip.getBoats().setTotalTime(diff);
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
        System.out.println("The diff is " + diff);
        return diff;
    }
    // retreive the income of all trips
    public double getIncomeAllTrips() {
        List<Trip> allTrips = tripRepository.findAll();
        double totalIncome = 0.0;
        for (int i = 0; i < allTrips.size(); i++) {
            totalIncome += allTrips.get(i).getBoats().getIncome();
        }
        return totalIncome;
}

// update electrical boat and set the availability after the charging time
public void updateElectricalBoat(Boat boat){
      int  chargingTime = boat.getChargingTime();
    System.out.println(chargingTime);
    // define a time to set a current time + charging time to change the availability to true
    Timer myTimer = new Timer();

    myTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            boat.setAvailable(true);
            boat.setStatus("Charging");
            boatRepository.save(boat);

        }
    }, (chargingTime *3600000) );
}


// retrieve used boats No for all trip
//    public List<Boat> getUsedBoatsForAllTrip() {
//        List<Trip> trips = tripRepository.findAll();
//        List<Boat> usedBoats = new ArrayList<>();
//        for (int i = 0; i < trips.size(); i++) {
//            boolean isUnique = false;
//            for (int j = 0; j < i; j++) {
//                if (trips.get(i).getBoats().get(i).getNo()
//                        .equalsIgnoreCase(trips.get(j).getBoats().get(j).getNo())) {
//                    isUnique = true;
//                    break;
//                }
//            }
//            if (!isUnique) {
//                usedBoats.add(trips.get(i).getBoats().get(i));
//            }
//        }
//        System.out.println(usedBoats);
//        return usedBoats;
//    }
}
