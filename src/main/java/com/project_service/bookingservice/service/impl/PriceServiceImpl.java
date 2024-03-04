package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.dto.PriceDto;
import com.project_service.bookingservice.entity.Price;
import com.project_service.bookingservice.mapper.PriceMapper;
import com.project_service.bookingservice.repository.PriceRepository;
import com.project_service.bookingservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    @Transactional
    public List<PriceDto> getPricesOfApartment(BookingDto bookingDto) {
        List<Price> pricesOfApartment = priceRepository.findPricesOfApartments(
                UUID.fromString(bookingDto.getApartmentId()), bookingDto.getStartDate(), bookingDto.getEndDate()
        );
        return priceMapper.mapToListDtos(pricesOfApartment);
    }
}
