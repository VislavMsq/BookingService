package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.ApartmentCategoryDTO;
import com.project_service.bookingservice.service.ApartmentCategoryService;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
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
class ApartmentCategoryControllerTest {
    @Autowired
    ApartmentCategoryService apartmentCategoryService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "user1@example.com")
    void createDTO() throws Exception {
        ApartmentCategoryDTO expected = new ApartmentCategoryDTO();
        expected.setName("test apartment 1");
        expected.setAbbreviation("ta1");
        expected.setType("APARTMENT");
        expected.setSleepPlace("3");

        String toCreate = objectMapper.writeValueAsString(expected);

        MvcResult mvcResultPost = mockMvc.perform(MockMvcRequestBuilders.post("/apartment_categories/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toCreate))
                .andReturn();

        assertEquals(201, mvcResultPost.getResponse().getStatus());

        ApartmentCategoryDTO created = objectMapper.readValue(mvcResultPost.getResponse().getContentAsString(), new TypeReference<>() {
        });
        String id = created.getId();
        expected.setId(id);
        assertEquals(created, expected);

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartment_categories/" + id))
                .andReturn();

        assertEquals(200, mvcResultGet.getResponse().getStatus());

        ApartmentCategoryDTO returned = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(returned, created);
    }

    @Test
    @WithUserDetails(value = "user1@example.com")
    void findApartmentCategoriesByOwner() throws Exception{
        List<ApartmentCategoryDTO> expectedList = new ArrayList<>();
        ApartmentCategoryDTO first = new ApartmentCategoryDTO();
        first.setId("ad99034d-4a69-492f-b65f-4aef01d21ee6");
        first.setName("Apartment Category 1");
        first.setAbbreviation("ABC1");
        first.setType("APARTMENT");
        first.setSleepPlace("2");

        ApartmentCategoryDTO second = new ApartmentCategoryDTO();
        second.setId("be2f0f46-9e36-4b99-8d62-8e498b783c38");
        second.setName("Apartment Category 2");
        second.setAbbreviation("ABC2");
        second.setType("ROOM");
        second.setSleepPlace("4");

        ApartmentCategoryDTO third = new ApartmentCategoryDTO();
        third.setId("d7f3cb48-dee2-11ee-bd3d-0242ac120002");
        third.setName("Apartment Category 1");
        third.setAbbreviation("ABC1");
        third.setType("APARTMENT");
        third.setSleepPlace("2");
        expectedList.add(first);
        expectedList.add(second);
        expectedList.add(third);

        MvcResult mvcResultGet = mockMvc.perform(MockMvcRequestBuilders.get("/apartment_categories"))
                .andReturn();

        LoggerFactory.getLogger(this.getClass()).info(mvcResultGet.getResponse().getContentAsString());


        assertEquals(200, mvcResultGet.getResponse().getStatus());

        List<ApartmentCategoryDTO> returnedList = objectMapper.readValue(mvcResultGet.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(returnedList, expectedList);

    }

}
