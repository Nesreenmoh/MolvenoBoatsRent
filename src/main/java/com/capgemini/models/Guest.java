package com.capgemini.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "GUEST")
public class Guest {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ID_TYPE")
    private String idType;

    @Column(name = "ID_NO")
    private String idNo;

    @Column(name = "PHONE")
    private String phone;

    @OneToMany(mappedBy = "guest",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Trip> guestTrips= new ArrayList<>();

    public Guest() {
    }

    public Guest(String name, String idType, String idNo, String phone) {
        this.name = name;
        this.idType = idType;
        this.idNo = idNo;
        this.phone = phone;
    }

    public Guest(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
