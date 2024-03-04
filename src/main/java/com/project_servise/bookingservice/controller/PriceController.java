package com.project_servise.bookingservice.controller;

import com.project_servise.bookingservice.dto.BookingDto;
import com.project_servise.bookingservice.dto.PriceDto;
import com.project_servise.bookingservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/prices")
public class PriceController {
    private final PriceService priceService;

    @GetMapping()
    public List<PriceDto> getPricesOfApartment(@RequestBody BookingDto bookingDto) {
        return priceService.getPricesOfApartment(bookingDto);
    }


}
