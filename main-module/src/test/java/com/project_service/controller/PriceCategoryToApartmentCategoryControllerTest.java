package com.project_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class PriceCategoryToApartmentCategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "user1@example.com")
    void create() throws Exception {
        PriceCategoryToApartmentCategoryDto categoryToCategoryDto = new PriceCategoryToApartmentCategoryDto();
        categoryToCategoryDto.setApartmentCategoryId("d7f3cb48-dee2-11ee-bd3d-0242ac120002");
        categoryToCategoryDto.setPriceCategoryId("f050448b-7a16-468c-8183-5f161a83db62");
        categoryToCategoryDto.setPrice(100.0);
        categoryToCategoryDto.setYear(2021);

        String categoryToCategoryDtoStr = objectMapper.writeValueAsString(categoryToCategoryDto);
        String categoryToCategoryCreationJson = mockMvc.perform(MockMvcRequestBuilders.post("/prices-to-apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryToCategoryDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PriceCategoryToApartmentCategoryDto categoryToCategoryCreation = objectMapper.readValue(categoryToCategoryCreationJson,
                PriceCategoryToApartmentCategoryDto.class);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/prices-to-apartments/" + categoryToCategoryCreation.getId()))
                .andExpect(status().isOk())
                .andReturn();

        PriceCategoryToApartmentCategoryDto returned = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PriceCategoryToApartmentCategoryDto.class);

        assertNotNull(categoryToCategoryCreation.getId());
        returned.setId(null);
        assertEquals("USD", returned.getCurrencyCode());
        categoryToCategoryDto.setCurrencyCode("USD");
        assertEquals(categoryToCategoryDto, returned);
    }

    @Test
    @WithUserDetails(value = "user1@example.com")
    void update() throws Exception {
        String existingId = "7d6f1727-f7aa-48a2-9971-161f30f3b497";
        double newPrice = 200.0;
        String newCurrencyCode = "EUR";

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/prices-to-apartments/" + existingId))
                .andExpect(status().isOk())
                .andReturn();

        PriceCategoryToApartmentCategoryDto existingCategoryToCategoryDto = objectMapper.readValue(result.getResponse().getContentAsString(), PriceCategoryToApartmentCategoryDto.class);

        double originalPrice = existingCategoryToCategoryDto.getPrice();
        String originalCurrencyCode = existingCategoryToCategoryDto.getCurrencyCode();

        existingCategoryToCategoryDto.setPrice(newPrice);
        existingCategoryToCategoryDto.setCurrencyCode(newCurrencyCode);

        String categoryToCategoryUpdateStr = objectMapper.writeValueAsString(existingCategoryToCategoryDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/prices-to-apartments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryToCategoryUpdateStr))
                .andExpect(status().isOk());

        MvcResult updatedResult = mockMvc.perform(MockMvcRequestBuilders.get("/prices-to-apartments/" + existingId))
                .andExpect(status().isOk())
                .andReturn();

        PriceCategoryToApartmentCategoryDto updatedCategoryToCategoryDto = objectMapper.readValue(updatedResult.getResponse().getContentAsString(), PriceCategoryToApartmentCategoryDto.class);

        assertNotEquals(originalPrice, updatedCategoryToCategoryDto.getPrice());
        assertNotEquals(originalCurrencyCode, updatedCategoryToCategoryDto.getCurrencyCode());
        assertEquals(newPrice, updatedCategoryToCategoryDto.getPrice());
        assertEquals(newCurrencyCode, updatedCategoryToCategoryDto.getCurrencyCode());
    }
}