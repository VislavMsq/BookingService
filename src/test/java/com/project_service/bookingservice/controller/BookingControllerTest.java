package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.dto.BoardDetailsOfRangeDto;
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
        BoardDetailsOfRangeDto boardDetailsOfRangeDto = getBoardDetailsOfRangeDto();

        String boardDetailsOfRangeDtoStr = objectMapper.writeValueAsString(boardDetailsOfRangeDto);
        String bookingDtoStr = objectMapper.writeValueAsString(bookingDto);

        //when
        String bookingCreationJson = mockMvc.perform(MockMvcRequestBuilders.post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookingDtoStr))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        BookingDto bookingDtoCreation = objectMapper.readValue(bookingCreationJson, BookingDto.class);

        String bookingJson = mockMvc.perform(MockMvcRequestBuilders.get("/bookings/" + bookingDtoCreation.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String borderDetailsOfRangeJson = mockMvc.perform(MockMvcRequestBuilders.get("/board_details/find_all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardDetailsOfRangeDtoStr))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        BookingDto bookingDtoResult = objectMapper.readValue(bookingJson, BookingDto.class);
        BoardDetailsOfRangeDto boardDetailDtoResult = objectMapper.readValue(borderDetailsOfRangeJson,
                BoardDetailsOfRangeDto.class);
        List<BoardDetailDto> boardDetailDtoExpected = getExepctedBoardDetailsList(bookingDtoResult.getId());
        List<BoardDetailDto> boardDetailDtoActual = boardDetailDtoResult.getBoardDetailDto();

        Assertions.assertNotNull(boardDetailDtoActual.get(0).getId());
        Assertions.assertNotNull(boardDetailDtoActual.get(1).getId());
        boardDetailDtoActual.get(0).setId(null);
        boardDetailDtoActual.get(1).setId(null);
        Assertions.assertEquals(boardDetailDtoExpected, boardDetailDtoActual);
        Assertions.assertNotNull(bookingDtoResult.getId());
        Assertions.assertEquals("NEW", bookingDtoResult.getStatus());
        bookingDtoResult.setId(null);
        bookingDtoResult.setStatus(null);
        Assertions.assertEquals(bookingDto, bookingDtoResult);
    }

    private static BoardDetailsOfRangeDto getBoardDetailsOfRangeDto() {
        BoardDetailsOfRangeDto boardDetailsOfRangeDto = new BoardDetailsOfRangeDto();
        boardDetailsOfRangeDto.setStart(LocalDate.of(2024, 2, 29));
        boardDetailsOfRangeDto.setFinish(LocalDate.of(2024, 3, 2));
        return boardDetailsOfRangeDto;
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

    private List<BoardDetailDto> getExepctedBoardDetailsList(String id) {

        BoardDetailDto boardDetailDto1 = getBoardDetailDto(id, 2, 29, 100.00);

        BoardDetailDto boardDetailDto2 = getBoardDetailDto(id, 3, 1, 150.00);

        return List.of(boardDetailDto1, boardDetailDto2);
    }

    private static BoardDetailDto getBoardDetailDto(String id, int month, int dayOfMonth, double price) {
        BoardDetailDto boardDetailDto1 = new BoardDetailDto();
        boardDetailDto1.setBookingId(id);
        boardDetailDto1.setDate(LocalDate.of(2024, month, dayOfMonth));
        boardDetailDto1.setApartmentId("a47ac10b-58cc-4372-a567-0e02b2c3d479");
        boardDetailDto1.setPrice(price);
        boardDetailDto1.setCurrencyId("3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20");
        boardDetailDto1.setApartmentSleepingPlace(2.0);
        boardDetailDto1.setApartmentCity("New York");
        boardDetailDto1.setClientName("Client One");
        return boardDetailDto1;
    }
}
