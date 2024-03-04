package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.PriceCategoryDto;
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

        String priceCategoryDtoStr = objectMapper.writeValueAsString(priceCategoryDto);

        // when
        String priceCategoryDtoJson = mockMvc.perform(MockMvcRequestBuilders.post("/price category/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(priceCategoryDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryDto priceCategoryDtoCreated = objectMapper.readValue(priceCategoryDtoJson, PriceCategoryDto.class);

        String id = priceCategoryDtoCreated.getId();

        String actualPriceCategoryJson = mockMvc.perform(MockMvcRequestBuilders.get("/price category/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // then

        PriceCategoryDto actualPriceCategoryDto = objectMapper.readValue(actualPriceCategoryJson, PriceCategoryDto.class);
        Assertions.assertNotNull(actualPriceCategoryDto.getId());
        actualPriceCategoryDto.setId(null);
        Assertions.assertEquals(priceCategoryDto,actualPriceCategoryDto);
    }
}