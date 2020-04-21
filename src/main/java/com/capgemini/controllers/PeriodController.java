package com.capgemini.controllers;

import com.capgemini.models.Season_Period;
import com.capgemini.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/periods")
public class PeriodController {

    @Autowired
    private PeriodService periodService;

    // get all  seasons periods
    @GetMapping
    public List<Season_Period> findAll(){
       return periodService.findAll();
    }

    // add a season period
    @PostMapping
    public Season_Period addPeriod(@RequestBody Season_Period season_period){
        return periodService.addPeriod(season_period);
    }

    // update a season period
    @PutMapping("/{id}")
    public Season_Period updatePeriod(@RequestBody Season_Period season_period, @PathVariable Long id){
        return periodService.updatePeriod(season_period, id);
    }

    // delete a season period
    @DeleteMapping("/{id}")
    public void deletePeriod(@PathVariable Long id){
        periodService.deletePeriod(id);
    }
}
