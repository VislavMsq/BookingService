package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
@AutoConfigureMockMvc
class ApartmentControllerTest {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void createApartmentTest() throws Exception {
        ApartmentDTO expected = new ApartmentDTO();
        expected.setName("Apartment 1");
        expected.setType("APARTMENT");
        expected.setCountry("USA");
        expected.setCity("New York");
        expected.setStreet("123 Main St");
        expected.setFloor("5");
        expected.setPet("true");
        expected.setSmoking("false");
        expected.setParkingPlace("1");
        expected.setDescription("Lorem ipsum");

        String toCreate = objectMapper.writeValueAsString(expected);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/apartments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andReturn();

        assertEquals(201, mvcResult.getResponse().getStatus());


        ApartmentDTO created = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, created);
    }


    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void findApartment() throws Exception {
        ApartmentDTO expected = new ApartmentDTO();
        expected.setName("Apartment 1");
        expected.setType("APARTMENT");
        expected.setCountry("USA");
        expected.setCity("New York");
        expected.setStreet("123 Main St");
        expected.setFloor("5");
        expected.setPet("true");
        expected.setSmoking("false");
        expected.setParkingPlace("1");
        expected.setDescription("Lorem ipsum");

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartments/{id}", "3f120739-8a84-4e21-84b3-7a66358157bf"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        ApartmentDTO returned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, returned);
    }

}
