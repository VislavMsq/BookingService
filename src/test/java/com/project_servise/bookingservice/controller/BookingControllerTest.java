package com.project_servise.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_servise.bookingservice.dto.BookingDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void createBooking() throws Exception {
        //given
        BookingDto bookingDto = new BookingDto();
        bookingDto.setApartmentId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        bookingDto.setCurrencyId("3b56fe6e-6910-4b0d-863e-ac60262e7a17");
        bookingDto.setClientId("0e288090-280c-489f-8058-bc36d534f3a5");
        bookingDto.setPrice(300.0);
        bookingDto.setStartDate(LocalDate.of(2024, 2, 29));
        bookingDto.setEndDate(LocalDate.of(2024, 3, 2));
        bookingDto.setIsEditedPrice(true);

        String bookingDtoStr = objectMapper.writeValueAsString(bookingDto);

        //when
        String id = mockMvc.perform(MockMvcRequestBuilders.post("/bookings/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String bookingJson = mockMvc.perform(MockMvcRequestBuilders.get("/bookings/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        BookingDto bookingDtoResult = objectMapper.readValue(bookingJson, BookingDto.class);

        Assertions.assertNotNull(bookingDtoResult.getId());
        Assertions.assertEquals("NEW", bookingDtoResult.getStatus());
        bookingDtoResult.setId(null);
        bookingDtoResult.setStatus(null);
        Assertions.assertEquals(bookingDto, bookingDtoResult);
    }
}