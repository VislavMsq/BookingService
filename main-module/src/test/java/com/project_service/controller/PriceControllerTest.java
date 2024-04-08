package com.project_service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.PriceDto;
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
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class PriceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void getPricesOfApartment() throws Exception {
        //given
        String apartmentId = "f47ac10b-58cc-4372-a567-0e02b2c3d479";

        //when
        String pricesResultStr = mockMvc.perform(MockMvcRequestBuilders.get("/prices/apartment/" + apartmentId)
                        .param("startDate", LocalDate.of(2024, 2, 29).toString())
                        .param("endDate", LocalDate.of(2024, 3, 1).toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<PriceDto> actual = objectMapper.readValue(pricesResultStr, new TypeReference<>() {
        });
        actual.forEach(p -> p.setId(null));

        Assertions.assertEquals(getExpectedPrices(apartmentId), actual);
    }

    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void updatePrices() throws Exception {
        //given
        String apartmentId1 = "e419d844-c317-4bf2-aa9b-e2d62cfafa31";
        String apartmentId2 = "1e9377bd-9083-4804-9ef8-c93acb999c01";
        String apartmentId3 = "eaa6efa1-9260-472d-88bb-832a9be95ce5";
        List<String> apartmentIds = List.of(apartmentId1, apartmentId2, apartmentId3);
        String apartmentIdsJson = objectMapper.writeValueAsString(apartmentIds);

        //when
        String pricesBeforeUpdateStr = mockMvc.perform(MockMvcRequestBuilders.get("/prices/apartment/" + apartmentId1)
                        .param("startDate", LocalDate.of(2024, 2, 29).toString())
                        .param("endDate", LocalDate.of(2024, 3, 1).toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.put("/prices")
                        .param("startDate", LocalDate.of(2024,1,1).toString())
                        .param("endDate", LocalDate.of(2024,12,31).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(apartmentIdsJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String pricesAfterUpdateStr = mockMvc.perform(MockMvcRequestBuilders.get("/prices/apartment/" + apartmentId1)
                        .param("startDate", LocalDate.of(2024, 2, 29).toString())
                        .param("endDate", LocalDate.of(2024, 3, 1).toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<PriceDto> pricesBeforeUpdate = objectMapper.readValue(pricesBeforeUpdateStr, new TypeReference<>() {});
        List<PriceDto> pricesAfterUpdate = objectMapper.readValue(pricesAfterUpdateStr, new TypeReference<>() {});

        Assertions.assertTrue(pricesBeforeUpdate.isEmpty());
        Assertions.assertFalse(pricesAfterUpdate.isEmpty());
        pricesAfterUpdate.forEach(p -> p.setId(null));
        Assertions.assertEquals(getExpectedUpdatedPrices(apartmentId1), pricesAfterUpdate);
    }

    private List<PriceDto> getExpectedPrices(String apartmentId) {
        PriceDto priceDto1 = new PriceDto();
        priceDto1.setPrice(100.00);
        priceDto1.setDate(LocalDate.of(2024, 2, 29));
        priceDto1.setIsEditedPrice(true);
        priceDto1.setPriority("LOW");
        priceDto1.setApartmentId(apartmentId);
        priceDto1.setCurrencyName("Yuan Renminbi");
        priceDto1.setCurrencyCode("CNY");

        PriceDto priceDto2 = new PriceDto();
        priceDto2.setPrice(100.00);
        priceDto2.setDate(LocalDate.of(2024, 3, 1));
        priceDto2.setIsEditedPrice(true);
        priceDto2.setPriority("HOLIDAY");
        priceDto2.setApartmentId(apartmentId);
        priceDto2.setCurrencyName("Yuan Renminbi");
        priceDto2.setCurrencyCode("CNY");

        return Arrays.asList(priceDto1, priceDto2);
    }

    private List<PriceDto> getExpectedUpdatedPrices(String apartmentId) {
        PriceDto priceDto1 = new PriceDto();
        priceDto1.setPrice(300.00);
        priceDto1.setDate(LocalDate.of(2024, 2, 29));
        priceDto1.setIsEditedPrice(false);
        priceDto1.setPriority("LOW");
        priceDto1.setApartmentId(apartmentId);
        priceDto1.setCurrencyName("Euro");
        priceDto1.setCurrencyCode("EUR");

        PriceDto priceDto2 = new PriceDto();
        priceDto2.setPrice(400.00);
        priceDto2.setDate(LocalDate.of(2024, 3, 1));
        priceDto2.setIsEditedPrice(false);
        priceDto2.setPriority("DEFAULT");
        priceDto2.setApartmentId(apartmentId);
        priceDto2.setCurrencyName("Euro");
        priceDto2.setCurrencyCode("EUR");

        return Arrays.asList(priceDto1, priceDto2);
    }
}
