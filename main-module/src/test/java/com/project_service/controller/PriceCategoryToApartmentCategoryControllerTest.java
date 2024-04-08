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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        categoryToCategoryDto.setCurrencyCode("USD");
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
        assertEquals(categoryToCategoryDto, returned);
    }
}