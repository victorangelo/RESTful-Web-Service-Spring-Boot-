package com.viktor.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.viktor.offer.Offer;
import com.viktor.repository.OffersRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OffersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OffersRepository offersRepository;

    @Test
    public void deletesOffer() throws Exception {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        
        Offer offer = new Offer("Offer", 10.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true);

        when(offersRepository.findById(1L)).thenReturn(Optional.ofNullable(offer));

        mockMvc.perform(delete("/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void postsNewOffer() throws Exception {
        when(offersRepository.save(any(Offer.class)))
                .thenReturn(new Offer() {{setId(1);}});

        mockMvc.perform(post("/")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content("{\"description\":\"Offer\",\"price\":5.99}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void getsAllOffers() throws Exception {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        Offer offer1 = new Offer("Offer 1", 7.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true);
        Offer offer2 = new Offer("Offer 2", 17.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true);
        
        List<Offer> offers = new ArrayList<Offer>();
        offers.add(offer1); 
        offers.add(offer2);

        when(offersRepository.findAll()).thenReturn(offers);

        mockMvc.perform(get("/ad"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].description", is("Offer 1")))
                .andExpect(jsonPath("$[0].price", is(7.99)))
                .andExpect(jsonPath("$[1].description", is("Offer 2")))
                .andExpect(jsonPath("$[1].price", is(17.99)));
    }

    @Test
    public void getsOffer() throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        Offer offer = new Offer("Offer", 11.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true);

        when(offersRepository.findById(1L)).thenReturn(Optional.ofNullable(offer));

        mockMvc.perform(get("/ad/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Offer")))
                .andExpect(jsonPath("$.price", is(11.99)));
    }
    
    @Test
    public void cancelsOffer() throws Exception {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        Offer offer = new Offer("Offer", 12.99, df.parse("2020-10-1 3:00 PM GMT+1:00"), true);

        when(offersRepository.findById(1L)).thenReturn(Optional.ofNullable(offer));

        mockMvc.perform(post("/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description", is("Offer")))
                .andExpect(jsonPath("$.price", is(12.99)))
        		.andExpect(jsonPath("$.valid", is(false)));
    }
    
    @Test
    public void getThrowsExceptionIfOfferNotFound() throws Exception {
        mockMvc.perform(get("/ad/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteThrowsExceptionIfOfferNotFound() throws Exception {
        mockMvc.perform(delete("/1"))
                .andExpect(status().isNotFound());
    }
}