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
    @WithUserDetails(value = "user1@example.com")
    void createPriceOfCategory() throws Exception {
        // given
        PriceCategoryDto priceCategoryDto = new PriceCategoryDto();
        priceCategoryDto.setName("My first season!");
        priceCategoryDto.setPriority("DEFAULT");
        priceCategoryDto.setCurrencyCode("EUR");

        ScheduleDto period = new ScheduleDto();
        period.setStartDate(LocalDate.of(2024, 7, 1));
        period.setEndDate(LocalDate.of(2024, 8, 15));

        priceCategoryDto.setPeriods(List.of(period));

        String priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDto);

        // when
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
    }

    @Test
    @WithUserDetails(value = "leonardo@gmail.com")
    void updatePriceCategory() throws Exception {

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price_category"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PriceCategoryDto> priceCategoryDtoList = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>(){});

        Assertions.assertFalse(priceCategoryDtoList.isEmpty());


        PriceCategoryDto priceCategoryDtoUpdate = priceCategoryDtoList.get(0);

        priceCategoryDtoUpdate
                .getPeriods()
                .get(0)
                .setEndDate(LocalDate.of(2024, 9, 1).minusDays(1));
        priceCategoryDtoUpdate
                .getPeriods()
                .get(0)
                .setStartDate(LocalDate.of(2024, 7, 1));

        String priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDtoUpdate);

        actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.post("/price_category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto actual = objectMapper.readValue(actualPriceCategoryJson, new TypeReference<>(){});

        PriceCategoryDto expected = new PriceCategoryDto();
        expected.setId(actual.getId());
        expected.setName("Standard");
        expected.setPriority("HIGH");
        expected.setCurrencyCode("EUR");

        ScheduleDto expectedPeriod = new ScheduleDto();
        expectedPeriod.setStartDate(LocalDate.of(2024, 7, 1));
        expectedPeriod.setEndDate(LocalDate.of(2024, 9, 1).minusDays(1));

        expected.setPeriods(List.of(expectedPeriod));

        Assertions.assertEquals(expected, actual);
    }
}
