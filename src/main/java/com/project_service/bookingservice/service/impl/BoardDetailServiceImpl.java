package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.entity.*;
import com.project_service.bookingservice.exception.PriceNotFoundException;
import com.project_service.bookingservice.mapper.BoardDetailMapper;
import com.project_service.bookingservice.repository.*;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.BoardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardDetailServiceImpl implements BoardDetailService {
    private final PriceRepository priceRepository;
    private final BoardDetailRepository boardDetailRepository;
    private final UserProvider userProvider;
    private final BoardDetailMapper boardDetailMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority('OWNER','WORKER')")
    public void createBoardOfDetail(Booking booking) {
        List<BoardDetail> boardDetailList = getBoardDetailList(booking);
        boardDetailRepository.saveAll(boardDetailList);
    }

    @Transactional
    @Override
    public List<BoardDetailDto> findAllBoardDetailDto(BoardDetailsFilterDto boardDetailsFilterDto) {
        User user = userProvider.getCurrentUser();
        User owner = user.getOwner() == null ? user : user.getOwner();
        List<String> apartmentIds = boardDetailsFilterDto.getApartmentIds();
        List<BoardDetail> boardDetailList = (apartmentIds == null || apartmentIds.isEmpty()) ?
                boardDetailRepository.findAllByOwnerInDateRange(owner, boardDetailsFilterDto.getStartDate(),
                        boardDetailsFilterDto.getEndDate()) :
                boardDetailRepository.findAllByApartmentsInDateRange(owner, apartmentIds,
                        boardDetailsFilterDto.getStartDate(), boardDetailsFilterDto.getEndDate());
        return boardDetailMapper.toList(boardDetailList);
    }

    private List<BoardDetail> getBoardDetailList(Booking booking) {
        List<BoardDetail> boardDetailList = new ArrayList<>();
        LocalDate startDate = booking.getStartDate();
        LocalDate endDate = booking.getEndDate();
        long dateRange = startDate.until(endDate, ChronoUnit.DAYS);
        for (int i = 0; i < dateRange; i++) {
            BoardDetail boardDetail = getBoardDetail(booking, startDate.plusDays(i));
            boardDetailList.add(boardDetail);
        }
        return boardDetailList;
    }

    private BoardDetail getBoardDetail(Booking booking, LocalDate localDate) {
        BoardDetail boardDetail = new BoardDetail();
        Price price = priceRepository.findByApartmentAndDate(booking.getApartment().getId(), localDate)
                .orElseThrow(() -> new PriceNotFoundException(String.format("Price of apartment with id %s not found",
                        booking.getApartment().getId())));
        boardDetail.setBooking(booking);
        boardDetail.setApartment(booking.getApartment());
        boardDetail.setCurrency(booking.getCurrency());
        boardDetail.setPrice(price.getPricePerDay());
        boardDetail.setDate(localDate);
        boardDetail.setClientName(String.format("%s %s", booking.getClient().getFirstName(), booking.getClient()
                .getLastName()));
        boardDetail.setApartmentCity(booking.getApartment().getCity());
        boardDetail.setApartmentSleepingPlace(booking.getApartment().getApartmentCategory().getSleepPlace());
        boardDetail.setOwner(booking.getOwner());
        boardDetail.setCreatedAt(LocalDateTime.now());
        boardDetail.setUpdatedAt(LocalDateTime.now());
        return boardDetail;
    }
}
