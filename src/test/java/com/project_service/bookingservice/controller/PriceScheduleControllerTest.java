package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.PriceScheduleDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.enums.Priority;
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
public class PriceScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "appolon12@gmail.com")
    void createScheduleTest() throws Exception {

        List<PriceCategoryDto> create = List.of(
                new PriceCategoryDto(
                        null,
                        "3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20",
                        "summer 2024",
                        Priority.HOLIDAY.toString(),
                        List.of(
                                new PriceScheduleDto(
                                        LocalDate.of(2024, 6, 1),
                                        LocalDate.of(2024, 1, 9).minusDays(1))
                        )
                )
        );

        String jsonReq = objectMapper.writeValueAsString(create);

        mockMvc.perform(MockMvcRequestBuilders.post("/price_schedules/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonReq))
                .andExpect(status().isCreated());


        String responseJson = mockMvc.perform(MockMvcRequestBuilders.get("/price_schedules")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


        List<ScheduleDto> actual = objectMapper.readValue(responseJson, new TypeReference<>() {
        });

        actual.get(0).setId(null);

        Assertions.assertEquals(1, actual.size());

        List<ScheduleDto> expected = List.of(new ScheduleDto(
                        null,
                        LocalDate.of(2024, 6, 1),
                        LocalDate.of(2024, 1, 9).minusDays(1)
                )
        );

        Assertions.assertEquals(expected, actual);
    }
}
