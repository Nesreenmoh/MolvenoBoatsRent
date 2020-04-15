package com.capgemini.repositories;

import com.capgemini.models.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest,Long> {

    public Guest findOneById(Long id);
    public Guest findOneByName(String name);
}
