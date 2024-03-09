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
        List<ApartmentDTO> expectedList = new ArrayList<>();
        ApartmentDTO first = new ApartmentDTO();
        first.setId("3f120739-8a84-4e21-84b3-7a66358157bf");
        first.setName("Apartment 1");
        first.setType("APARTMENT");
        first.setCountry("USA");
        first.setCity("New York");
        first.setStreet("123 Main St");
        first.setFloor("5");
        first.setPet("true");
        first.setSmoking("false");
        first.setParkingPlace("1");
        first.setDescription("Lorem ipsum");
        first.setApartmentCategoryId("ad99034d-4a69-492f-b65f-4aef01d21ee6");
        first.setParentId(null);

        ApartmentDTO second = new ApartmentDTO();
        second.setId("8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2");
        second.setName("Apartment 2");
        second.setType("ROOM");
        second.setCountry("UK");
        second.setCity("London");
        second.setStreet("456 Oak St");
        second.setFloor("3");
        second.setPet("false");
        second.setSmoking("true");
        second.setParkingPlace("2");
        second.setDescription("Dolor sit amet");
        second.setApartmentCategoryId("be2f0f46-9e36-4b99-8d62-8e498b783c38");

        ApartmentDTO third = new ApartmentDTO();
        third.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        third.setName("Apartment 1");
        third.setType("APARTMENT");
        third.setCountry("USA");
        third.setCity("New York");
        third.setStreet("Broadway");
        third.setFloor("2");
        third.setPet("true");
        third.setSmoking("false");
        third.setParkingPlace("1");
        third.setDescription("Cozy studio apartment in the heart of New York City");
        third.setApartmentCategoryId(null);
        third.setParentId(null);

        ApartmentDTO fourth = new ApartmentDTO();
        fourth.setId("1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d");
        fourth.setName("Apartment 2");
        fourth.setType("APARTMENT");
        fourth.setCountry("USA");
        fourth.setCity("Los Angeles");
        fourth.setStreet("Hollywood Blvd");
        fourth.setFloor("3");
        fourth.setPet("false");
        fourth.setSmoking("true");
        fourth.setParkingPlace("2");
        fourth.setDescription("Spacious apartment with Hollywood sign view");
        fourth.setApartmentCategoryId(null);
        fourth.setParentId(null);

        ApartmentDTO fifth = new ApartmentDTO();
        fifth.setId("eccbc87e-4b5c-4331-a025-6545673431ef");
        fifth.setName("Apartment 3");
        fifth.setType("ROOM");
        fifth.setCountry("Canada");
        fifth.setCity("Toronto");
        fifth.setStreet("Yonge Street");
        fifth.setFloor("4");
        fifth.setPet("true");
        fifth.setSmoking("true");
        fifth.setParkingPlace("0");
        fifth.setDescription("Modern house with a beautiful view of Lake Ontario");
        fifth.setApartmentCategoryId(null);
        fifth.setParentId(null);

        expectedList.add(first);
        expectedList.add(second);
        expectedList.add(third);
        expectedList.add(fourth);
        expectedList.add(fifth);

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
        ApartmentDTO first = new ApartmentDTO();
        first.setId("3f120739-8a84-4e21-84b3-7a66358157bf");
        first.setName("Apartment 1");
        first.setType("APARTMENT");
        first.setCountry("USA");
        first.setCity("New York");
        first.setStreet("123 Main St");
        first.setFloor("5");
        first.setPet("true");
        first.setSmoking("false");
        first.setParkingPlace("1");
        first.setDescription("Lorem ipsum");
        first.setApartmentCategoryId("ad99034d-4a69-492f-b65f-4aef01d21ee6");
        first.setParentId(null);

        ApartmentDTO second = new ApartmentDTO();
        second.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        second.setName("Apartment 1");
        second.setType("APARTMENT");
        second.setCountry("USA");
        second.setCity("New York");
        second.setStreet("Broadway");
        second.setFloor("2");
        second.setPet("true");
        second.setSmoking("false");
        second.setParkingPlace("1");
        second.setDescription("Cozy studio apartment in the heart of New York City");
        second.setApartmentCategoryId(null);
        second.setParentId(null);

        ApartmentDTO third = new ApartmentDTO();
        third.setId("1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d");
        third.setName("Apartment 2");
        third.setType("APARTMENT");
        third.setCountry("USA");
        third.setCity("Los Angeles");
        third.setStreet("Hollywood Blvd");
        third.setFloor("3");
        third.setPet("false");
        third.setSmoking("true");
        third.setParkingPlace("2");
        third.setDescription("Spacious apartment with Hollywood sign view");
        third.setApartmentCategoryId(null);
        third.setParentId(null);

        List<ApartmentDTO> expectedList = new ArrayList<>();
        expectedList.add(first);
        expectedList.add(second);
        expectedList.add(third);

        String country = "USA";

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/country/" + country))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedList, returnedList);
    }


    @Test
    @WithUserDetails(value = "user1@example.com")
    void findApartmentByCity() throws Exception{
        ApartmentDTO first = new ApartmentDTO();
        first.setId("3f120739-8a84-4e21-84b3-7a66358157bf");
        first.setName("Apartment 1");
        first.setType("APARTMENT");
        first.setCountry("USA");
        first.setCity("New York");
        first.setStreet("123 Main St");
        first.setFloor("5");
        first.setPet("true");
        first.setSmoking("false");
        first.setParkingPlace("1");
        first.setDescription("Lorem ipsum");
        first.setApartmentCategoryId("ad99034d-4a69-492f-b65f-4aef01d21ee6");
        first.setParentId(null);

        ApartmentDTO second = new ApartmentDTO();
        second.setId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        second.setName("Apartment 1");
        second.setType("APARTMENT");
        second.setCountry("USA");
        second.setCity("New York");
        second.setStreet("Broadway");
        second.setFloor("2");
        second.setPet("true");
        second.setSmoking("false");
        second.setParkingPlace("1");
        second.setDescription("Cozy studio apartment in the heart of New York City");
        second.setApartmentCategoryId(null);
        second.setParentId(null);

        List<ApartmentDTO> expectedList = new ArrayList<>();
        expectedList.add(first);
        expectedList.add(second);

        String city = "New York";

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/city/" + city))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expectedList, returnedList);
    }
}
