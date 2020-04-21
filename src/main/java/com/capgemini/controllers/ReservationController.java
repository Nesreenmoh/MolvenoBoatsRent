package com.capgemini.controllers;

import com.capgemini.models.Reservation;
import com.capgemini.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // get all reservation
    @GetMapping
    public List<Reservation> getAllReservation(){
      return reservationService.findAllReservations();
    }

    // get a reservation by id
    @GetMapping("/{resId}")
    public Reservation getOneById(@PathVariable Long resId){

        return reservationService.findOneByID(resId);
    }

    // get all cancelled reservation
    @GetMapping("/cancelled")
    public List<Reservation> findAllCancelledReservation(){
        return reservationService.findAllCancelledReservations();
    }

    // add a reservation
    @PostMapping
    public void addReservation(@RequestBody Reservation reservation) throws ParseException {
        reservationService.addReservation(reservation);
    }

    // update reservation or cancel a reservation
    @PutMapping("/{resId}")
    public void updateReservation(@RequestBody Reservation reservation, Long id){
        reservationService.updateReservation(reservation);
    }
}
