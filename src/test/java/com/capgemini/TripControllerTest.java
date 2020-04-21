package com.capgemini;

import com.capgemini.controllers.TripController;
import com.capgemini.models.Trip;
import com.capgemini.services.TripService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TripControllerTest {

    @InjectMocks
    private TripController tripController;

    @Mock
    private TripService tripService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(tripController).build();
    }

//    testing get all trips request
    @Test
    public void getAllTripsTestMethod() throws Exception {
        Trip trip1 = new Trip(LocalDateTime.of(2020,04,15, 5,1,45,36912), "ongoing");
        Trip trip2 = new Trip(LocalDateTime.of(2020,04,11, 7,1,45,36912), "ended");

        List<Trip> tripList = new ArrayList<>();
        tripService.addTrip(trip1);
        tripService.addTrip(trip2);
        tripList.add(trip1);
        tripList.add(trip2);
        when(tripService.findAllTrips()).thenReturn(tripList);

        mockMvc.perform(get("/api/trips"))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].status", Matchers.is("ongoing")))
                .andExpect(jsonPath("$.[1].status", Matchers.is("ended")))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tripService,times(1)).findAllTrips();
    }

   // test get all ended trips method in the trip controller
    @Test
    public void getEndedTripTestMethod() throws Exception {
        Trip trip1 = new Trip(LocalDateTime.of(2020,04,15, 5,1,45,36912), "ended");
        trip1.setId(13L);
        Trip trip2 = new Trip(LocalDateTime.of(2020,04,11, 7,1,45,36912), "ended");
        trip2.setId(14L);

        List<Trip> tripList = new ArrayList<>();
        tripService.addTrip(trip1);
        tripService.addTrip(trip2);
        tripList.add(trip1);
        tripList.add(trip2);
        when(tripService.findAllEndedTrips()).thenReturn(tripList);

        mockMvc.perform(get("/api/trips/ended"))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].id", Matchers.is(13)))
                .andExpect(jsonPath("$.[1].id", Matchers.is(14)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tripService,times(1)).findAllEndedTrips();
    }

    // test post request of the trip
    @Test
    public void postTripTestMethod() throws Exception {
        Trip trip1 = new Trip();
        trip1.setId(13L);
        trip1.setStatus("ongoing");
        tripService.addTrip(trip1);
        System.out.println(trip1);
        when(tripService.addTrip(Mockito.any(Trip.class))).thenReturn(trip1);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(trip1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
               .andExpect(jsonPath("$.id", Matchers.is((13))))
                .andExpect(jsonPath("$.status", Matchers.is(trip1.getStatus())))
                .andExpect(status().isOk());

        verify(tripService,times(2)).addTrip(Mockito.any(Trip.class));
    }
}
