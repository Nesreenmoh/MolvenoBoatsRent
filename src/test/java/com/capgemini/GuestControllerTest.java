package com.capgemini;

import com.capgemini.controllers.GuestController;

import com.capgemini.models.Guest;

import com.capgemini.services.GuestService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.Mockito;

import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
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

    // test Post guest request in guest controller
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

    // test get all guests in guest controller
    @Test
    public void getAllGuestsTestMethod() throws Exception {
        Guest guest1 = new Guest("Mohammed", "ID", "53453463","12345" );
        guest1.setId(14L);
        guestService.addGuest(guest1);
        List<Guest> myGuests= new ArrayList<>();
        myGuests.add(guest1);
        when(guestService.findAllGuest()).thenReturn(myGuests);

        mockMvc.perform(get("/api/guests"))
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.[0].id", Matchers.is(14)))
                .andExpect(jsonPath("$.[0].name", Matchers.is("Mohammed")))
                .andExpect(jsonPath("$.[0].idNo", Matchers.is("53453463")))
                .andExpect(jsonPath("$.[0].idType", Matchers.is("ID")))
                .andExpect(jsonPath("$.[0].phone", Matchers.is("12345")))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(guestService, times(1)).findAllGuest();
    }


    // test update Guest request in guest controller
    @Test
    public void updateGuestTest() throws Exception {
        Guest guest1 = new Guest("Mohammed", "ID", "53453463","12345" );
        guest1.setId(13L);
        guestService.addGuest(guest1);

        when(guestService.findOneGuest(13L)).thenReturn(guest1);
        guest1.setName("Nesreen");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(guest1);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/api/guests/13")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals("Nesreen", guest1.getName());
        assertEquals(200, status);
    }

    //test a delete  guest request in guest controller
    @Test
    public void deleteGuestRequestTest() throws Exception{
        Guest guest1 = new Guest("Mohammed", "ID", "53453463","12345" );
        guest1.setId(13L);
        guestService.addGuest(guest1);
        when(guestService.deleteGuest(13L)).thenReturn("A record has been Deleted");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/api/guests/13")).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content=  mvcResult.getResponse().getContentAsString();
        System.out.println(content);


    }


}
