package com.capgemini.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PERIOD")
public class Season_Period {


    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private double rate;


    // constructors
    public Season_Period() {
    }

    public Season_Period(String name, String startDate, String endDate, double rate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rate = rate;
    }

    // getter and setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
