package com.capgemini.repositories;

import com.capgemini.models.Boat;
import com.capgemini.models.BoatType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    Boat findOneById(Long id);
    Boat findOneByNo(String no);
    List<Boat> findAllByType(BoatType type);
}
