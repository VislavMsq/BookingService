package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.dto.CreateApartmentDTO;
import com.project_service.bookingservice.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mapstruct.MappingConstants.NULL;

@SpringBootTest
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
@AutoConfigureMockMvc
public class ApartmentControllerTest {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createApartmentTest() throws Exception{
        CreateApartmentDTO create = new CreateApartmentDTO();
        create.setName("Apartment 1");
        create.setType("APARTMENT");
        create.setCountry("USA");
        create.setCity("New York");
        create.setStreet("123 Main St");
        create.setFloor("5");
        create.setPet("true");
        create.setSmoking("false");
        create.setParkingPlace("1");
        create.setDescription("Lorem ipsum");

        String toCreate = objectMapper.writeValueAsString(create);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/apartments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());


        ApartmentDTO expected = new ApartmentDTO();
        expected.setCity("New York");
        expected.setCountry("USA");
        expected.setName("Apartment 1");
        expected.setStreet("123 Main St");

        ApartmentDTO created =  objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, created);
    }


    @Test
    void findApartment() throws Exception{
        ApartmentDTO expected = new ApartmentDTO();
        expected.setCity("New York");
        expected.setCountry("USA");
        expected.setName("Apartment 1");
        expected.setStreet("Broadway");

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartments/{id}", "f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        ApartmentDTO returned =  objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, returned);
    }

    @Test
    void findAllApartments() {
    }
}
