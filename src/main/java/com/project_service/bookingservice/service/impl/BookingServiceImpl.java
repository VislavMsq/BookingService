package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.entity.Apartment;
import com.project_service.bookingservice.entity.Booking;
import com.project_service.bookingservice.entity.Client;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.entity.enums.Status;
import com.project_service.bookingservice.exception.ApartmentNotFoundException;
import com.project_service.bookingservice.exception.BookingNotFoundException;
import com.project_service.bookingservice.exception.ClientNotFoundException;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.mapper.BookingMapper;
import com.project_service.bookingservice.repository.ApartmentRepository;
import com.project_service.bookingservice.repository.BookingRepository;
import com.project_service.bookingservice.repository.ClientRepository;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ApartmentRepository apartmentRepository;
    private final ClientRepository clientRepository;
    private final CurrencyRepository currencyRepository;
    private final BookingMapper bookingMapper;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public Booking createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.mapToEntity(bookingDto);
        User owner = userProvider.getCurrentUser();
        Apartment apartment = apartmentRepository.findById(UUID.fromString(bookingDto.getApartmentId()))
                .orElseThrow(() -> new ApartmentNotFoundException(String.format("Apartment with id %s not found",
                        bookingDto.getApartmentId())));
        Client client = clientRepository.findById(UUID.fromString(bookingDto.getClientId()))
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id %s not found",
                        bookingDto.getClientId())));
        Currency currency = currencyRepository.findById(UUID.fromString(bookingDto.getCurrencyId()))
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with id %s not found",
                        bookingDto.getCurrencyId())));
        booking.setOwner(owner);
        booking.setApartment(apartment);
        booking.setClient(client);
        booking.setCurrency(currency);
        booking.setStatus(Status.NEW);
        bookingRepository.save(booking);
        return booking;
    }

    @Override
    @Transactional
    public BookingDto findById(String id) {
        return bookingMapper.mapToDto(bookingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BookingNotFoundException(String.format("Booking with id %s not found",
                        id))));
    }
}
