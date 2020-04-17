package com.capgemini.models;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "TRIP")
public class Trip {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "START_TIME")
    private LocalDateTime startTime;
    @Column(name = "END_TIME")
    private LocalDateTime endTime;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DURATION")
    private Long duration=0L;

    @ManyToOne(cascade = CascadeType.ALL)
    private Guest guest;

    @ManyToOne(cascade = CascadeType.ALL)
    private Boat boats;


    public Trip() {
    }

    public Trip(LocalDateTime startTime, String status) {
        this.startTime = startTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Boat getBoats() {
        return boats;
    }

    public void setBoats(Boat boats) {
        this.boats = boats;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", status='" + status + '\'' +
                ", duration=" + duration +
                '}';
    }
}
