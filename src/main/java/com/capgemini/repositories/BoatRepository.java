package com.capgemini.repositories;

import com.capgemini.models.Boat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatRepository extends JpaRepository<Boat, Long> {
    Boat findOneById(Long id);
    Boat findOneByNo(String no);

}
