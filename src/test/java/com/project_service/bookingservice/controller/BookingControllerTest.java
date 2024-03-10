package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.BookingDto;
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
        BookingDto bookingDto = getBookingDto();
        String bookingDtoStr = objectMapper.writeValueAsString(bookingDto);

        //when
        String creationResultJson = mockMvc.perform(MockMvcRequestBuilders.post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookingDto bookingDtoCreationResult = objectMapper.readValue(creationResultJson, BookingDto.class);

        String bookingJson = mockMvc.perform(MockMvcRequestBuilders
                        .get("/bookings/" + bookingDtoCreationResult.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        BookingDto bookingDtoActual = objectMapper.readValue(bookingJson, BookingDto.class);

        Assertions.assertNotNull(bookingDtoActual.getId());
        Assertions.assertEquals("NEW", bookingDtoActual.getStatus());
        bookingDtoActual.setId(null);
        bookingDtoActual.setStatus(null);
        Assertions.assertEquals(bookingDto, bookingDtoActual);
    }

    private static BookingDto getBookingDto() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setApartmentId("f47ac10b-58cc-4372-a567-0e02b2c3d479");
        bookingDto.setCurrencyId("3b56fe6e-6910-4b0d-863e-ac60262e7a17");
        bookingDto.setClientId("0e288090-280c-489f-8058-bc36d534f3a5");
        bookingDto.setPrice(300.0);
        bookingDto.setStartDate(LocalDate.of(2024, 2, 29));
        bookingDto.setEndDate(LocalDate.of(2024, 3, 2));
        bookingDto.setIsEditedPrice(true);
        return bookingDto;
    }
}
