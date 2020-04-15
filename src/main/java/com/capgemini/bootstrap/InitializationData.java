package com.capgemini.bootstrap;

import com.capgemini.models.Boat;
import com.capgemini.models.BoatType;
import com.capgemini.models.Guest;
import com.capgemini.models.Trip;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.GuestRepository;
import com.capgemini.repositories.TripRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class InitializationData implements CommandLineRunner {

    private final BoatRepository boatRepository;
    private final TripRepository tripRepository;
    private final GuestRepository guestRepository;

    public InitializationData(BoatRepository boatRepository, TripRepository tripRepository, GuestRepository guestRepository) {
        this.boatRepository = boatRepository;
        this.tripRepository = tripRepository;
        this.guestRepository = guestRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Guest guest1 = new Guest("Nesreen Al-shargabi", "Driving License", "67856749", "78967856");
        guestRepository.save(guest1);


        Boat boat1 = new Boat("1234", 4, "Electrical", 100.0, 200.0, 2);
        Trip trip = new Trip(LocalDateTime.of(2020,04,15, 5,1,45,36912), "ongoing");
        trip.setGuest(guest1);
        List<Trip> trip1 = new ArrayList<>();
        trip1.add(trip);

        boat1.setTrips(trip1);
        trip.setBoats(boat1);
        boatRepository.save(boat1);
        tripRepository.save(trip);
    }
}
