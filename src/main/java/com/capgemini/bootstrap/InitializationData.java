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


        Boat boat1 = new Boat("1001", 2, "Rowing", 100.0, 200.0, 0);
        Boat boat2 = new Boat("1002", 2, "Rowing", 100.0, 200.0, 0);
        Boat boat3 = new Boat("1003", 6, "Rowing", 100.0, 400.0, 0);
        Boat boat4 = new Boat("1004", 6, "Electrical", 100.0, 400.0, 2);
        Boat boat5 = new Boat("1005", 8, "Electrical", 100.0, 400.0,1 );
        Boat boat6 = new Boat("1006", 4, "Raft", 100.0, 300.0, 0);
        Boat boat7 = new Boat("1007", 6, "Raft", 100.0, 400.0, 0);
        Boat boat8 = new Boat("1008", 8, "Raft", 100.0, 200.0, 0);

        Trip trip1 = new Trip(LocalDateTime.of(2020,04,15, 5,1,45,36912), "ongoing");
        Trip trip2 = new Trip(LocalDateTime.of(2020,04,15, 5,1,45,36912), "ongoing");
        trip1.setGuest(guest1);
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);

        boat1.setTrips(trips);
        trip1.setBoats(boat1);
        boatRepository.save(boat1);
        boatRepository.save(boat2);
        boatRepository.save(boat3);
        boatRepository.save(boat4);
        boatRepository.save(boat5);
        boatRepository.save(boat6);
        boatRepository.save(boat7);
        boatRepository.save(boat8);

        tripRepository.save(trip1);
        tripRepository.save(trip2);
    }
}
