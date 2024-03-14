package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

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
    @WithUserDetails(value = "aloha.test@gmail.com")
    void createPriceOfCategory() throws Exception {
        // given
        PriceCategoryDto priceCategoryDto = new PriceCategoryDto();
        priceCategoryDto.setName("My first season!");
        priceCategoryDto.setPriority("DEFAULT");
        priceCategoryDto.setCurrencyCode("EUR");

        ScheduleDto period = new ScheduleDto();
        period.setStartDate(LocalDate.of(2024, 1, 1));
        period.setEndDate(LocalDate.of(2025, 1, 1).minusDays(1));

        priceCategoryDto.setPeriods(List.of(period));

        String priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDto);

        // when: phase 1
        String priceCategoryDtoJson = mockMvc.perform(MockMvcRequestBuilders.post("/price_category/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto priceCategoryDtoCreated = objectMapper.readValue(priceCategoryDtoJson, PriceCategoryDto.class);

        String id = priceCategoryDtoCreated.getId();
        priceCategoryDto.setId(id);

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price_category/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then

        PriceCategoryDto actualPriceCategoryDto = objectMapper.readValue(actualPriceCategoryJson, PriceCategoryDto.class);
        Assertions.assertNotNull(actualPriceCategoryDto.getId());

        Assertions.assertEquals(priceCategoryDto, actualPriceCategoryDto);

        // phase 2
        actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price_category"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PriceCategoryDto> priceCategoryDtoList = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>(){});

        Assertions.assertFalse(priceCategoryDtoList.isEmpty());

        // phase 3
        PriceCategoryDto priceCategoryDtoUpdate = priceCategoryDtoList.get(0);

        priceCategoryDtoUpdate
                .getPeriods()
                .get(0)
                .setEndDate(LocalDate.of(2025, 10, 1).minusDays(1));

        priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDtoList.get(0));

        actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.post("/price_category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto actual = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>(){});

        Assertions.assertEquals(priceCategoryDtoUpdate, actual);
    }
}
