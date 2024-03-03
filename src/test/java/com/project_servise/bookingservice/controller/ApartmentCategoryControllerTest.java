package com.project_servise.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.service.ApartmentCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
public class ApartmentCategoryControllerTest {
    @Autowired
    ApartmentCategoryService apartmentCategoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void createDTO() throws Exception{
        ApartmentCategoryCreateDTO expected = new ApartmentCategoryCreateDTO();
        expected.setName("Apartment Category 1");
        expected.setAbbreviation("ABC1");
        expected.setType("Studio");
        expected.setSleepPlace("2");

        String toCreate = objectMapper.writeValueAsString(expected);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/apartments_category/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        ApartmentCategoryCreateDTO created = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, created);
    }

    @Test
    void findCategory() throws Exception {
        ApartmentCategoryCreateDTO expected = new ApartmentCategoryCreateDTO();
        expected.setName("Apartment Category 1");
        expected.setAbbreviation("ABC1");
        expected.setType("Studio");
        expected.setSleepPlace("2");

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartments_category/{id}", "ad99034d-4a69-492f-b65f-4aef01d21ee6"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        ApartmentCategoryCreateDTO created = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(expected, created);
    }

    @Test
    void findAllCategories() throws Exception{
        List<ApartmentCategoryCreateDTO> expectedList = new ArrayList<>();

        ApartmentCategoryCreateDTO expected1 = new ApartmentCategoryCreateDTO();
        expected1.setName("Apartment Category 1");
        expected1.setAbbreviation("ABC1");
        expected1.setType("Studio");
        expected1.setSleepPlace("2");

        ApartmentCategoryCreateDTO expected2 = new ApartmentCategoryCreateDTO();
        expected2.setName("Apartment Category 2");
        expected2.setAbbreviation("ABC2");
        expected2.setType("One Bedroom");
        expected2.setSleepPlace("4");

        expectedList.add(expected1);
        expectedList.add(expected2);

        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartments_category/"))
                .andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());

        List<ApartmentCategoryCreateDTO> list = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(list, expectedList);
    }
}
