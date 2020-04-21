package com.capgemini.repositories;

import com.capgemini.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

     Reservation findOneById(Long id);
     List<Reservation>findAllByResDate(LocalDate selectedDate);
}

