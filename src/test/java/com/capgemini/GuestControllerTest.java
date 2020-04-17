package com.capgemini;

import com.capgemini.controllers.GuestController;
import com.capgemini.models.Boat;
import com.capgemini.models.Guest;
import com.capgemini.services.BoatService;
import com.capgemini.services.GuestService;
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
public class GuestControllerTest {

    @InjectMocks
    private GuestController guestController;

    @Mock
    private GuestService guestService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(guestController).build();
    }

    @Test
    public void addGuestsTestMethod() throws Exception {

        Guest guest1 = new Guest("Mohammed", "ID", "53453463","12345" );
        guestService.addGuest(guest1);
        when(guestService.addGuest(Mockito.any(Guest.class))).thenReturn(guest1);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(guest1);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/guests")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(jsonPath("$.id", Matchers.is((guest1.getId()))))
                .andExpect(jsonPath("$.name", Matchers.is(guest1.getName())))
                .andExpect(status().isOk());



    }
}
