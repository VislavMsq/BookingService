package com.project_service.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.dto.BoardDetailDto;
import com.project_service.dto.BookingDto;
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
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "appolon12@gmail.com")
    void createBooking() throws Exception {
        //given
        String apartmentId = "a47ac10b-58cc-4372-a567-0e02b2c3d479";
        BookingDto bookingDto = getBookingDto(apartmentId);

        List<BoardDetailDto> boardDetailDtoExpected = getExepctedBoardDetailsList(apartmentId);

        String bookingDtoStr = objectMapper.writeValueAsString(bookingDto);

        //when
        String bookingCreationJson = mockMvc.perform(MockMvcRequestBuilders.post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String boardDetailsListJson = mockMvc.perform(MockMvcRequestBuilders.get("/board-details")
                        .param("startDate", LocalDate.of(2024, 2, 29).toString())
                        .param("endDate", LocalDate.of(2024, 3, 2).toString()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        BookingDto bookingDtoActual = objectMapper.readValue(bookingCreationJson, BookingDto.class);
        List<BoardDetailDto> boardDetailDtoActual = objectMapper.readValue(boardDetailsListJson, new TypeReference<>() {
        });

        Assertions.assertNotNull(bookingDtoActual.getId());
        Assertions.assertEquals("NEW", bookingDtoActual.getStatus());
        bookingDtoActual.setId(null);
        bookingDtoActual.setStatus(null);
        Assertions.assertEquals(bookingDto, bookingDtoActual);

        Assertions.assertNotNull(boardDetailDtoActual.get(0).getId());
        Assertions.assertNotNull(boardDetailDtoActual.get(0).getBookingId());
        Assertions.assertNotNull(boardDetailDtoActual.get(1).getId());
        Assertions.assertNotNull(boardDetailDtoActual.get(1).getBookingId());
        boardDetailDtoActual.get(0).setId(null);
        boardDetailDtoActual.get(0).setBookingId(null);
        boardDetailDtoActual.get(1).setId(null);
        boardDetailDtoActual.get(1).setBookingId(null);
        Assertions.assertEquals(boardDetailDtoExpected, boardDetailDtoActual);
    }

    private static BookingDto getBookingDto(String apartmentId) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setApartmentId(apartmentId);
        bookingDto.setCurrencyCode("USD");
        bookingDto.setClientId("f9f5e56d-740e-4a37-bcce-1c5c6781d5f8");
        bookingDto.setPrice(150.0);
        bookingDto.setStartDate(LocalDate.of(2024, 2, 29));
        bookingDto.setEndDate(LocalDate.of(2024, 3, 2));
        bookingDto.setIsEditedPrice(true);
        return bookingDto;
    }

    private List<BoardDetailDto> getExepctedBoardDetailsList(String apartmentId) {
        BoardDetailDto boardDetailDto1 = getBoardDetailDto(apartmentId, 2, 29);
        BoardDetailDto boardDetailDto2 = getBoardDetailDto(apartmentId, 3, 1);
        return List.of(boardDetailDto1, boardDetailDto2);
    }

    private static BoardDetailDto getBoardDetailDto(String apartmentId, int month, int dayOfMonth) {
        BoardDetailDto boardDetailDto = new BoardDetailDto();
        boardDetailDto.setDate(LocalDate.of(2024, month, dayOfMonth));
        boardDetailDto.setApartmentId(apartmentId);
        boardDetailDto.setPrice(100.0);
        boardDetailDto.setCurrencyCode("USD");
        boardDetailDto.setApartmentSleepingPlace(2.0);
        boardDetailDto.setApartmentCity("New York");
        boardDetailDto.setClientName("Client One");
        return boardDetailDto;
    }
}
