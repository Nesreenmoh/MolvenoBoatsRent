package com.capgemini.services;

import com.capgemini.models.Reservation;
import com.capgemini.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Transactional
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    // add reservation controller
    public void addReservation(Reservation reservation) throws ParseException {
        // parse the reservation date to get the time
        String dataString = reservation.getResDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date =  myFormat.parse(dataString);
        System.out.println("my start time is: " + date);
        String time = new SimpleDateFormat("hh:mm a").format(date);
        System.out.println(time);
        reservationRepository.save(reservation);
    }

    public void updateReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}
