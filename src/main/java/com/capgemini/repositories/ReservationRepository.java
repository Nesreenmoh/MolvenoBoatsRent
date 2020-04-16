package com.capgemini.repositories;

import com.capgemini.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public Reservation findOneById(Long id);
    public List<Reservation>findAllByResDate(LocalDate selectedDate);
}

