package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.dto.PriceDto;
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
        Result result = getResult();

        String bookingDtoStr = objectMapper.writeValueAsString(result.bookingDto());

        //when
        String pricesResultStr = mockMvc.perform(MockMvcRequestBuilders.get("/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        List<PriceDto> actual = objectMapper.readValue(pricesResultStr, new TypeReference<>() {
        });
        Assertions.assertEquals(result.expected(), actual);

    }

    private Result getResult() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setApartmentId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        bookingDto.setStartDate(LocalDate.of(2024, 2, 29));
        bookingDto.setEndDate(LocalDate.of(2024, 3, 2));
        List<PriceDto> expected = getExpectedPrices();
        return new Result(bookingDto, expected);
    }

    private record Result(BookingDto bookingDto, List<PriceDto> expected) {
    }

    private List<PriceDto> getExpectedPrices() {
        PriceDto priceDto1 = new PriceDto();
        priceDto1.setPrice(100.00);
        priceDto1.setDate(LocalDate.of(2024, 2, 29));
        priceDto1.setIsEditedPrice(false);
        priceDto1.setApartmentId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        priceDto1.setCurrencyName("Yuan Renminbi");
        priceDto1.setCurrencyCode("CNY");
        priceDto1.setPriority(1);

        PriceDto priceDto2 = new PriceDto();
        priceDto2.setPrice(150.00);
        priceDto2.setDate(LocalDate.of(2024, 3, 1));
        priceDto2.setIsEditedPrice(false);
        priceDto2.setApartmentId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        priceDto2.setCurrencyName("Yuan Renminbi");
        priceDto2.setCurrencyCode("CNY");
        priceDto2.setPriority(1);

        return Arrays.asList(priceDto1, priceDto2);
    }
}
