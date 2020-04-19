package com.capgemini.services;

import com.capgemini.models.Guest;
import com.capgemini.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    // retrieve all guests
    public List<Guest> findAllGuest(){
       return guestRepository.findAll();
    }

    // retrieve one Guest
    public Guest findOneGuest(Long id){
        return guestRepository.findOneById(id);
    }

    // add guest
    public Guest addGuest(Guest guest){
       return  guestRepository.save(guest);

    }

    // retrieve guest by name
    public Guest findOneByName(String name){
        return guestRepository.findOneByName(name);
    }

    // update Guest data
    public void updateGuest(Guest guest, Long id){
     guestRepository.save(guest);
    }

    // delete a guest

    public String deleteGuest(Long id){
        guestRepository.deleteById(id);
        return "Deleted";
    }

}
