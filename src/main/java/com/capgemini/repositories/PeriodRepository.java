package com.capgemini.repositories;

import com.capgemini.models.Season_Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Season_Period,Long> {
}
