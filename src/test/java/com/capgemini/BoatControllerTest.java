package com.capgemini;

import com.capgemini.controllers.BoatController;
import com.capgemini.models.Boat;
import com.capgemini.services.BoatService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
    public void GetAllTestMethod() throws Exception {
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

}
