package com.capgemini.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String resDate;
    private String res_start_time;
    private String res_end_time;
    private Integer duration;
    private Boolean cancel=false;
    private String status;

    @ManyToOne
    private Guest guest;

    @ManyToOne
    private Boat boat;

    // constructors
    public Reservation() {
    }

    public Reservation(String resDate, Integer duration, String status) {
        this.resDate = resDate;
        this.duration = duration;
        this.status = status;
    }

    // setters and getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResDate() {
        return resDate;
    }

    public void setResDate(String resDate) {
        this.resDate = resDate;
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



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRes_start_time() {
        return res_start_time;
    }

    public void setRes_start_time(String res_start_time) {
        this.res_start_time = res_start_time;
    }

    public String getRes_end_time() {
        return res_end_time;
    }

    public void setRes_end_time(String res_end_time) {
        this.res_end_time = res_end_time;
    }
}

