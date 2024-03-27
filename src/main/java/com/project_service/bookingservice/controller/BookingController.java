package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable("id") String id) {
        return bookingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingDto createBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }
}
