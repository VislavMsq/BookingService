package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class PriceCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "user1@example.com")
    void createPriceOfCategory() throws Exception {
        // given
        PriceCategoryDto priceCategoryDto = new PriceCategoryDto();
        priceCategoryDto.setName("My first season!");
        priceCategoryDto.setPriority("DEFAULT");
        priceCategoryDto.setCurrencyCode("EUR");

        ScheduleDto period = new ScheduleDto();
        period.setStartDate("7-1");
        period.setEndDate("8-15");

        priceCategoryDto.setPeriods(Set.of(period));

        String priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDto);

        // when
        String priceCategoryDtoJson = mockMvc.perform(MockMvcRequestBuilders.post("/price-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto priceCategoryDtoCreated = objectMapper.readValue(priceCategoryDtoJson, PriceCategoryDto.class);

        String id = priceCategoryDtoCreated.getId();
        priceCategoryDto.setId(id);

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price-category/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then
        PriceCategoryDto actualPriceCategoryDto = objectMapper.readValue(actualPriceCategoryJson, PriceCategoryDto.class);
        assertNotNull(actualPriceCategoryDto.getId());

        assertEquals(priceCategoryDto, actualPriceCategoryDto);
    }

    @Test
    @WithUserDetails(value = "leonardo@gmail.com")
    void updatePriceCategory() throws Exception {

        String id = "75335102-dff9-4a66-b576-80f0b017548c";

        PriceCategoryDto expected = new PriceCategoryDto();
        expected.setId(id);
        expected.setCurrencyCode("USD");
        expected.setPriority("LOW");
        expected.setName("Socks");

        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setStartDate("7-1");
        scheduleDto.setEndDate("8-31");

        expected.setPeriods(Set.of(scheduleDto));

        String priceCategoryDtoStr = objectMapper.writeValueAsString(expected);

        mockMvc.perform(MockMvcRequestBuilders.put("/price-category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isOk());

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price-category/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto actual = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>() {
        });

        assertEquals(expected, actual);
    }

    @Test
    @WithUserDetails(value = "leonardo@gmail.com")
    void findAll() throws Exception {

        PriceCategoryDto priceCategoryDto1 = new PriceCategoryDto();
        priceCategoryDto1.setId("75335102-dff9-4a66-b576-80f0b017548c");
        priceCategoryDto1.setCurrencyCode("EUR");
        priceCategoryDto1.setName("Standard");
        priceCategoryDto1.setPriority("HIGH");
        ScheduleDto scheduleDto1 = new ScheduleDto();
        scheduleDto1.setStartDate("6-15");
        scheduleDto1.setEndDate("8-31");
        priceCategoryDto1.setPeriods(Set.of(scheduleDto1));

        PriceCategoryDto priceCategoryDto2 = new PriceCategoryDto();
        priceCategoryDto2.setId("75435102-dff9-4a66-b576-80f0b017548c");
        priceCategoryDto2.setCurrencyCode("EUR");
        priceCategoryDto2.setName("Standard");
        priceCategoryDto2.setPriority("DEFAULT");
        ScheduleDto scheduleDto2 = new ScheduleDto();
        scheduleDto2.setStartDate("1-1");
        scheduleDto2.setEndDate("12-31");
        priceCategoryDto2.setPeriods(Set.of(scheduleDto2));

        List<PriceCategoryDto> expected = List.of(priceCategoryDto1, priceCategoryDto2);

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price-category"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PriceCategoryDto> actual = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>() {
        });

        assertEquals(expected, actual);
    }
}
