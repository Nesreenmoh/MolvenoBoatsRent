package com.capgemini.services;

import com.capgemini.models.Season_Period;
import com.capgemini.repositories.PeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeriodService {

    @Autowired
    private PeriodRepository periodRepository;


    // find all
    public List<Season_Period> findAll() {
       return periodRepository.findAll();
    }

    // add a period
    public Season_Period addPeriod( Season_Period season_period) {
        return periodRepository.save(season_period);
    }

    // update a period

    public Season_Period updatePeriod(Season_Period season_period, Long id) {
        return periodRepository.save(season_period);
    }

    // delete a period
    public void deletePeriod(Long id) {
        periodRepository.deleteById(id);
    }
}
