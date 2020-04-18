package com.capgemini;

import com.capgemini.controllers.BoatController;
import com.capgemini.models.Boat;
import com.capgemini.services.BoatService;
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


import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BoatControllerTest {

    @InjectMocks
    private BoatController boatController;

    @Mock
    private BoatService boatService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(boatController).build();
    }

    // test get method in boat controller
    @Test
    public void boatFindAllTestMethod() throws Exception {
        Boat boat1 = new Boat("1009", 4,"Rowing", 100.0, 200.0, 0 );
        List<Boat> boatList = new ArrayList<>();
        boatList.add(boat1);
        boatService.addBoat(boat1);
        when(boatService.getAllBoats()).thenReturn(boatList);

        mockMvc.perform(get("/api/boats"))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].no", Matchers.is("1009")))
                .andExpect(jsonPath("$.[0].noOfSeats", Matchers.is(4)))
                .andExpect(jsonPath("$.[0].type", Matchers.is("Rowing")))
                .andExpect(jsonPath("$.[0].minPrice", Matchers.is(100.0)))
                .andExpect(jsonPath("$.[0].accPrice", Matchers.is(200.0)))
                .andExpect(jsonPath("$.[0].chargingTime", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(boatService, times(1)).getAllBoats();

    }

   // test the request of find boats by type
    @Test
    public void boatFindByTypeTestMethod() throws Exception {
        Boat boat1 = new Boat("1009", 4,"Rowing", 100.0, 200.0, 0 );
        Boat boat2 = new Boat("1010", 6,"Rowing", 100.0, 300.0, 0 );
        List<Boat> rowingList= new ArrayList<>();
        rowingList.add(boat1);
        rowingList.add(boat2);
        boatService.addBoat(boat1);
        boatService.addBoat(boat2);
        when(boatService.getBoatByType("rowing")).thenReturn(rowingList);

        mockMvc.perform(get("/api/boats/type/rowing"))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].no", Matchers.is("1009")))
                .andExpect(jsonPath("$.[0].noOfSeats", Matchers.is(4)))
                .andExpect(jsonPath("$.[1].no", Matchers.is("1010")))
                .andExpect(jsonPath("$.[1].noOfSeats", Matchers.is(6)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(boatService, times(1)).getBoatByType("rowing");

    }

    // testing POST request
    @Test
    public void boatPostTestMethod() throws Exception {
        Boat boat1 = new Boat("1009", 4,"Rowing", 100.0, 200.0, 0 );
        boatService.addBoat(boat1);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(boat1);

        when(boatService.addBoat(Mockito.any(Boat.class))).thenReturn(boat1);

        verify(boatService, times(1)).addBoat(Mockito.any(Boat.class));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/boats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.no", Matchers.is((boat1.getNo()))))
                .andExpect(jsonPath("$.noOfSeats", Matchers.is(boat1.getNoOfSeats())))
                .andExpect(status().isOk());

    }

    @Test
    public void BoatPutTestMethod() throws Exception {
        Boat boat1 = new Boat("1009", 4,"Rowing", 100.0, 200.0, 0 );
        boat1.setId(13L);
        boatService.addBoat(boat1);

        when(boatService.getOneBoat(13L)).thenReturn(boat1);
        boat1.setNo("1010");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(boat1);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/boats/13")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertEquals("1010", boat1.getNo());

    }


}
