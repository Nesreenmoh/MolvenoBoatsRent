package com.capgemini.services;

import com.capgemini.models.Boat;
import com.capgemini.models.Reservation;
import com.capgemini.repositories.BoatRepository;
import com.capgemini.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Transactional
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BoatRepository boatRepository;

    // add reservation controller
    public void addReservation(Reservation reservation) throws ParseException {
        // parse the reservation date to get the time
        String dataString = reservation.getResDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date =  myFormat.parse(dataString);
        System.out.println("my Date is: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR,reservation.getDuration());
        Date endTime = calendar.getTime();
        String startTime = new SimpleDateFormat("hh:mm a").format(date);
        String endTimeFormat = new SimpleDateFormat("hh:mm a").format(endTime);
        reservation.setRes_start_time(startTime);
        System.out.println("start time "+ startTime);
        System.out.println("end time " + endTimeFormat);
        reservation.setRes_end_time(endTimeFormat);

        reservationRepository.save(reservation);

    }

    public void updateReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }

    public List<Reservation> findAllReservations() {
        return reservationRepository.findAll();
    }
}
