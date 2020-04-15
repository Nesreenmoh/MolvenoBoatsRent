package com.capgemini.controllers;

import com.capgemini.models.Guest;
import com.capgemini.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    // request to get all guests
    @GetMapping
    public List<Guest> getAllGuests(){
       return guestService.findAllGuest();
    }

    // request to get one guest
    @GetMapping("/{id}")
    public Guest getOneGuest(@PathVariable Long id){
        return guestService.findOneGuest(id);
    }

    @GetMapping("/name/{name}")
    public Guest getOneByname(@PathVariable String name){
       return guestService.findOneByName(name);
    }
    @PostMapping
    public Guest addGuest(@RequestBody Guest guest){

        return guestService.addGuest(guest);
    }

}
