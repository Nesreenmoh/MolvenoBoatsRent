package com.capgemini.services;

import com.capgemini.models.Boat;
import com.capgemini.models.Reservation;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.GuestRepository;
import com.capgemini.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.util.*;

@Transactional
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private GuestRepository guestRepository;

    public int counter = 0;

    // add reservation controller
    public void addReservation(Reservation reservation) throws ParseException {

        // parse the reservation date to get the time
        String dataString = reservation.getResDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = myFormat.parse(dataString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, reservation.getDuration());
        Date endTime = calendar.getTime();
        String startTime = new SimpleDateFormat("hh:mm a").format(date);
        String endTimeFormat = new SimpleDateFormat("hh:mm a").format(endTime);

        //checking if the boat type is raft
        if (reservation.getBoat().getType().equalsIgnoreCase("Raft")) {
            counter++;
            if (counter == 4) {
                reservation.getBoat().setAvailable(true);
                counter = 0;
            }
        }

        System.out.println("counter is " + counter);
        // setting  a time to the reservation fields
        reservation.setRes_start_time(startTime);
        reservation.setRes_end_time(endTimeFormat);
        reservationTimer(reservation);
        // reservation.getBoat().setStatus("Reserved");

        // saving the data in the repository
        boatRepository.save(reservation.getBoat());
        guestRepository.save(reservation.getGuest());
        reservationRepository.save(reservation);

    }

    // making a timer for a reservation to set the boat status as reserved
    public void reservationTimer(Reservation reservation) throws ParseException {
        // parse the reservation date
        String dataString = reservation.getResDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = myFormat.parse(dataString);
        System.out.println("my parse date is " + date);
        //set a timer
        Timer myTimer = new Timer();

        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                reservation.getBoat().setAvailable(false);
                reservation.getBoat().setStatus("Reserved");
                boatRepository.save(reservation.getBoat());

            }// run this function after a charging time of the boat in millieseconds
        }, date);

    }

    // update reservation
    public void updateReservation(Reservation reservation) {
        reservation.setStatus("Cancelled");
        reservation.getBoat().setStatus("Active");
        boatRepository.save(reservation.getBoat());
        reservationRepository.save(reservation);
    }

    // find all cancelled reservation
    public List<Reservation> findAllCancelledReservations() {
        List<Reservation> myList = new ArrayList<>();
        List<Reservation> retrievedList = reservationRepository.findAll();
        for (int i = 0; i < retrievedList.size(); i++) {
            if (retrievedList.get(i).getStatus().equalsIgnoreCase("Cancelled")) {
                myList.add(retrievedList.get(i));
            }
        }
        return myList;
    }

    // find all reservation
    public List<Reservation> findAllReservations() {
        List<Reservation> myList = new ArrayList<>();
        List<Reservation> reservationList = reservationRepository.findAll();
        for (int i = 0; i < reservationList.size(); i++) {
            if (reservationList.get(i).getStatus().equalsIgnoreCase("Active")) {
                myList.add(reservationList.get(i));
            }
        }
        return myList;
    }

    // find a reservation by Id

    public Reservation findOneByID(Long id) {
        return reservationRepository.findOneById(id);
    }

}
