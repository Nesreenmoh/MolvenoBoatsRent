package com.capgemini.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOAT", uniqueConstraints = {@UniqueConstraint(columnNames = {"BOAT_NO"})})
public class Boat implements Comparable<Boat>{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "BOAT_NO")
    private String no;
    @NotNull
    @Column(name = "NO_OF_SEATS")
    private Integer noOfSeats;
    @Column(name = "BOAT_TYPE")
    private String type;
    @Column(name = "AVAILABILE")
    private Boolean available = true;
    @Column(name = "MAINTENANCE")
    private Boolean maintenance = false;
    @Column(name = "INCOME")
    private Double income;
    @Column(name = "MIN_PRICE")
    private Double minPrice;
    @Column(name = "ACTUAL_PRICE")
    private Double accPrice;
    @Column(name = "TOTAL_TIME")
    private Long totalTime = 0L;
    @Column(name = "CHARGING_TIME")
    private Integer chargingTime = 0;
    @Column(name = "BOAT_STATUS")
    private String status = "Active";

    @OneToMany(mappedBy = "boats",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Trip> trips = new ArrayList<>();


    public Boat() {
    }

    public Boat(String no, @NotNull Integer noOfSeats, String type, Double minPrice, Double accPrice, Integer chargingTime) {
        this.no = no;
        this.noOfSeats = noOfSeats;
        this.minPrice = minPrice;
        this.type=type;
        this.accPrice = accPrice;
        this.chargingTime = chargingTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Boolean maintenance) {
        this.maintenance = maintenance;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getAccPrice() {
        return accPrice;
    }

    public void setAccPrice(Double accPrice) {
        this.accPrice = accPrice;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime += totalTime;
    }

    public Integer getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "Boat{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", noOfSeats=" + noOfSeats +
                ", type=" + type +
                ", available=" + available +
                ", maintenance=" + maintenance +
                ", income=" + income +
                ", minPrice=" + minPrice +
                ", accPrice=" + accPrice +
                '}';
    }

// implementation of a comparable method that will order fetching boats by Total Time used
    @Override
    public int compareTo(Boat boat) {
        return this.getTotalTime().compareTo(boat.getTotalTime());
    }
}
