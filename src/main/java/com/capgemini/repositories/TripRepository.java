package com.capgemini.repositories;

import com.capgemini.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    public Trip findOneById(Long id);
}
