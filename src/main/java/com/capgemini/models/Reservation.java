package com.capgemini.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate resDate;
    private LocalTime res_start_time;
    private LocalTime res_end_time;
    private Boolean cancel=false;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Boat boat;

    // constructors
    public Reservation() {
    }

    public Reservation(LocalDate resDate, LocalTime res_start_time, Guest guest) {
        this.resDate = resDate;
        this.res_start_time = res_start_time;
        this.guest = guest;
    }

    // setters and getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getResDate() {
        return resDate;
    }

    public void setResDate(LocalDate resDate) {
        this.resDate = resDate;
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

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public LocalTime getRes_end_time() {
        return res_end_time;
    }

    public void setRes_end_time(LocalTime res_end_time) {
        this.res_end_time = res_end_time;
    }
}

