package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.ApartmentCategoryDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
@AutoConfigureMockMvc
class ApartmentCategoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "user1@example.com")
    void createApartmentCategory() throws Exception {
        ApartmentCategoryDto expected = new ApartmentCategoryDto();
        expected.setName("test apartment 1");
        expected.setAbbreviation("ta1");
        expected.setType("APARTMENT");
        expected.setSleepPlace("3");

        String toCreate = objectMapper.writeValueAsString(expected);

        String apartmentCategoryCreatedJson = mockMvc.perform(MockMvcRequestBuilders.post("/apartment-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApartmentCategoryDto created = objectMapper.readValue(apartmentCategoryCreatedJson, new TypeReference<>() {
        });
        String id = created.getId();
        expected.setId(id);
        assertEquals(created, expected);

        String apartmentCategoryDtoJson = mockMvc.perform(
                        MockMvcRequestBuilders.get("/apartment-categories/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ApartmentCategoryDto returned = objectMapper.readValue(apartmentCategoryDtoJson, new TypeReference<>() {
        });

        assertEquals(returned, created);
    }

    @Test
    @WithUserDetails(value = "user1@example.com")
    void findApartmentCategoriesByOwner() throws Exception {
        List<ApartmentCategoryDto> expectedList = new ArrayList<>();
        ApartmentCategoryDto first = new ApartmentCategoryDto();
        first.setId("ad99034d-4a69-492f-b65f-4aef01d21ee6");
        first.setName("Apartment Category 1");
        first.setAbbreviation("ABC1");
        first.setType("APARTMENT");
        first.setSleepPlace("2");

        ApartmentCategoryDto second = new ApartmentCategoryDto();
        second.setId("be2f0f46-9e36-4b99-8d62-8e498b783c38");
        second.setName("Apartment Category 2");
        second.setAbbreviation("ABC2");
        second.setType("ROOM");
        second.setSleepPlace("4");

        ApartmentCategoryDto third = new ApartmentCategoryDto();
        third.setId("d7f3cb48-dee2-11ee-bd3d-0242ac120002");
        third.setName("Apartment Category 1");
        third.setAbbreviation("ABC1");
        third.setType("APARTMENT");
        third.setSleepPlace("2");
        expectedList.add(first);
        expectedList.add(second);
        expectedList.add(third);


        String categoriesListJson = mockMvc.perform(MockMvcRequestBuilders.get("/apartment-categories"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ApartmentCategoryDto> returnedList = objectMapper.readValue(categoriesListJson, new TypeReference<>() {
        });

        assertEquals(returnedList, expectedList);

    }

}
