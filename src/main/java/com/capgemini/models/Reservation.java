package com.capgemini.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate reservation_date;
    private LocalTime res_start_time;
    private Boolean cancel=false;

    // constructors
    public Reservation() {
    }

    public Reservation(LocalDate reservation_date, LocalTime res_start_time) {
        this.reservation_date = reservation_date;
        this.res_start_time = res_start_time;
    }

    // setters and getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(LocalDate reservation_date) {
        this.reservation_date = reservation_date;
    }

    public LocalTime getRes_start_time() {
        return res_start_time;
    }

    public void setRes_start_time(LocalTime res_start_time) {
        this.res_start_time = res_start_time;
    }

    public Boolean getCancel() {
        return cancel;
    }

    public void setCancel(Boolean cancel) {
        this.cancel = cancel;
    }
}

