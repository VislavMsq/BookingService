package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
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
        BookingDto bookingDto = getBookingDto();
        BoardDetailsFilterDto boardDetailsFilterDto = getBoardDetailsFilterDto();
        List<BoardDetailDto> boardDetailDtoExpected = getExepctedBoardDetailsList();

        String boardDetailsOfRangeDtoStr = objectMapper.writeValueAsString(boardDetailsFilterDto);
        String bookingDtoStr = objectMapper.writeValueAsString(bookingDto);

        //when
        String bookingCreationJson = mockMvc.perform(MockMvcRequestBuilders.post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String boardDetailsListJson = mockMvc.perform(MockMvcRequestBuilders.get("/board_details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardDetailsOfRangeDtoStr))
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

    private static BoardDetailsFilterDto getBoardDetailsFilterDto() {
        BoardDetailsFilterDto boardDetailsFilterDto = new BoardDetailsFilterDto();
        boardDetailsFilterDto.setStart(LocalDate.of(2024, 2, 29));
        boardDetailsFilterDto.setFinish(LocalDate.of(2024, 3, 2));
        return boardDetailsFilterDto;
    }

    private static BookingDto getBookingDto() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setApartmentId("a47ac10b-58cc-4372-a567-0e02b2c3d479");
        bookingDto.setCurrencyId("3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20");
        bookingDto.setClientId("f9f5e56d-740e-4a37-bcce-1c5c6781d5f8");
        bookingDto.setPrice(150.0);
        bookingDto.setStartDate(LocalDate.of(2024, 2, 29));
        bookingDto.setEndDate(LocalDate.of(2024, 3, 2));
        bookingDto.setIsEditedPrice(true);
        return bookingDto;
    }

    private List<BoardDetailDto> getExepctedBoardDetailsList() {
        BoardDetailDto boardDetailDto1 = getBoardDetailDto(2, 29, 100.00);
        BoardDetailDto boardDetailDto2 = getBoardDetailDto(3, 1, 150.00);
        return List.of(boardDetailDto1, boardDetailDto2);
    }

    private static BoardDetailDto getBoardDetailDto(int month, int dayOfMonth, double price) {
        BoardDetailDto boardDetailDto = new BoardDetailDto();
        boardDetailDto.setDate(LocalDate.of(2024, month, dayOfMonth));
        boardDetailDto.setApartmentId("a47ac10b-58cc-4372-a567-0e02b2c3d479");
        boardDetailDto.setPrice(price);
        boardDetailDto.setCurrencyId("3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20");
        boardDetailDto.setApartmentSleepingPlace(2.0);
        boardDetailDto.setApartmentCity("New York");
        boardDetailDto.setClientName("Client One");
        return boardDetailDto;
    }
}
