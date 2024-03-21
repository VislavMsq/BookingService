package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.dto.PriceDto;
import com.project_service.bookingservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping
    public List<PriceDto> getPricesOfApartment(@RequestBody BookingDto bookingDto) {
        return priceService.getPricesOfApartment(bookingDto);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping
    public ResponseEntity<Void> updatePrices(@RequestBody BoardDetailsFilterDto boardDetailsFilterDto) {
        priceService.updatePrices(boardDetailsFilterDto);
        return ResponseEntity.ok().build();
    }
}
