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

import java.util.ArrayList;
import java.util.List;

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
        expected.setApartmentCategoryId("be2f0f46-9e36-4b99-8d62-8e498b783c38");
        expected.setParentId("3f120739-8a84-4e21-84b3-7a66358157bf");

        String toCreate = objectMapper.writeValueAsString(expected);

        MvcResult mvcResultPost = mockMvc.perform(MockMvcRequestBuilders.post("/apartments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andReturn();

        assertEquals(201, mvcResultPost.getResponse().getStatus());


        ApartmentDTO created = objectMapper.readValue(mvcResultPost.getResponse().getContentAsString(), new TypeReference<>() {
        });
        String id = created.getId();
        expected.setId(id);
        assertEquals(expected, created);

        MvcResult mvcResultGet = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartments/" + id))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        ApartmentDTO returned = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, returned);
    }

    @Test
    @WithUserDetails(value = "user1@example.com")
    void findAllApartments() throws Exception {
        List<ApartmentDTO> expectedList = expectListFindAllApartments();

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartments"))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedList, returnedList);
    }

    @Test
    @WithUserDetails(value = "user1@example.com")
    void findApartmentByCountry() throws Exception {
        List<ApartmentDTO> expectedList = expectListFindApartmentsByCountry();

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/country/USA"))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedList, returnedList);
    }


    @Test
    @WithUserDetails(value = "user1@example.com")
    void findApartmentByCity() throws Exception{

        List<ApartmentDTO> expectedList = expectListFindApartmentsByCity();

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/city/New York"))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedList, returnedList);
    }

    private List<ApartmentDTO> expectListFindAllApartments() {
        List<ApartmentDTO> apartments = new ArrayList<>();

        apartments.add(createApartment("3f120739-8a84-4e21-84b3-7a66358157bf", "Apartment 1", "APARTMENT", "USA", "New York", "123 Main St", "5", "true", "false", "1", "Lorem ipsum", "ad99034d-4a69-492f-b65f-4aef01d21ee6", null));
        apartments.add(createApartment("8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2", "Apartment 2", "ROOM", "UK", "London", "456 Oak St", "3", "false", "true", "2", "Dolor sit amet", "be2f0f46-9e36-4b99-8d62-8e498b783c38", null));
        apartments.add(createApartment("f47ac10b-58cc-4372-a567-0e02b2c3d479", "Apartment 1", "APARTMENT", "USA", "New York", "Broadway", "2", "true", "false", "1", "Cozy studio apartment in the heart of New York City", null, null));
        apartments.add(createApartment("1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d", "Apartment 2", "APARTMENT", "USA", "Los Angeles", "Hollywood Blvd", "3", "false", "true", "2", "Spacious apartment with Hollywood sign view", null, null));
        apartments.add(createApartment("eccbc87e-4b5c-4331-a025-6545673431ef", "Apartment 3", "ROOM", "Canada", "Toronto", "Yonge Street", "4", "true", "true", "0", "Modern house with a beautiful view of Lake Ontario", null, null));

        return apartments;
    }

    private List<ApartmentDTO> expectListFindApartmentsByCountry() {
        List<ApartmentDTO> apartments = new ArrayList<>();

        apartments.add(createApartment("3f120739-8a84-4e21-84b3-7a66358157bf", "Apartment 1", "APARTMENT", "USA", "New York", "123 Main St", "5", "true", "false", "1", "Lorem ipsum", "ad99034d-4a69-492f-b65f-4aef01d21ee6", null));
        apartments.add(createApartment("f47ac10b-58cc-4372-a567-0e02b2c3d479", "Apartment 1", "APARTMENT", "USA", "New York", "Broadway", "2", "true", "false", "1", "Cozy studio apartment in the heart of New York City", null, null));
        apartments.add(createApartment("1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d", "Apartment 2", "APARTMENT", "USA", "Los Angeles", "Hollywood Blvd", "3", "false", "true", "2", "Spacious apartment with Hollywood sign view", null, null));

        return apartments;
    }

    private List<ApartmentDTO> expectListFindApartmentsByCity() {
        List<ApartmentDTO> apartments = new ArrayList<>();

        apartments.add(createApartment("3f120739-8a84-4e21-84b3-7a66358157bf", "Apartment 1", "APARTMENT", "USA", "New York", "123 Main St", "5", "true", "false", "1", "Lorem ipsum", "ad99034d-4a69-492f-b65f-4aef01d21ee6", null));
        apartments.add(createApartment("f47ac10b-58cc-4372-a567-0e02b2c3d479", "Apartment 1", "APARTMENT", "USA", "New York", "Broadway", "2", "true", "false", "1", "Cozy studio apartment in the heart of New York City", null, null));

        return apartments;
    }

    private ApartmentDTO createApartment(String id, String name, String type, String country, String city, String street, String floor, String pet, String smoking, String parkingPlace, String description, String apartmentCategoryId, String parentId) {
        ApartmentDTO apartment = new ApartmentDTO();
        apartment.setId(id);
        apartment.setName(name);
        apartment.setType(type);
        apartment.setCountry(country);
        apartment.setCity(city);
        apartment.setStreet(street);
        apartment.setFloor(floor);
        apartment.setPet(pet);
        apartment.setSmoking(smoking);
        apartment.setParkingPlace(parkingPlace);
        apartment.setDescription(description);
        apartment.setApartmentCategoryId(apartmentCategoryId);
        apartment.setParentId(parentId);
        return apartment;
    }
}
