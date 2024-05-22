package com.project_service.controller.impl;

import com.project_service.controller.BookingOperationsService;
import com.project_service.dto.BookingDto;
import com.project_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/bookings")
public class BookingController implements BookingOperationsService {
    private final BookingService bookingService;

    @GetMapping("/{id}")
    public BookingDto findById(@PathVariable("id") String id) {
        return bookingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('OWNER','WORKER')")
    public BookingDto createBooking(@RequestBody BookingDto bookingDto) {
        return bookingService.createBooking(bookingDto);
    }
}
