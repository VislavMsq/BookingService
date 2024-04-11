package com.project_service.service.impl;


import com.project_service.dto.BookingDto;
import com.project_service.entity.*;
import com.project_service.entity.enums.Role;
import com.project_service.entity.enums.Status;
import com.project_service.exception.ApartmentNotFoundException;
import com.project_service.exception.BookingNotFoundException;
import com.project_service.exception.ClientNotFoundException;
import com.project_service.exception.CurrencyNotFoundException;
import com.project_service.mapper.BookingMapper;
import com.project_service.repository.ApartmentRepository;
import com.project_service.repository.BookingRepository;
import com.project_service.repository.ClientRepository;
import com.project_service.repository.CurrencyRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.BoardDetailService;
import com.project_service.service.BookingService;
import com.project_service.service.UtilsService;
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
    private final BoardDetailService boardDetailService;
    private final BookingMapper bookingMapper;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = bookingMapper.mapToEntity(bookingDto);
        User currentUser = userProvider.getCurrentUser();
        User owner = currentUser.getRole().equals(Role.OWNER) ? currentUser : currentUser.getOwner();
        Apartment apartment = apartmentRepository.findById(UUID.fromString(bookingDto.getApartmentId()))
                .orElseThrow(() -> new ApartmentNotFoundException(String.format("Apartment with id %s not found",
                        bookingDto.getApartmentId())));
        UtilsService.checkOwner(apartment, owner);
        Client client = clientRepository.findById(UUID.fromString(bookingDto.getClientId()))
                .orElseThrow(() -> new ClientNotFoundException(String.format("Client with id %s not found",
                        bookingDto.getClientId())));
        UtilsService.checkOwner(client, owner);
        Currency currency = currencyRepository.findByCode(bookingDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency code %s not found",
                        bookingDto.getCurrencyCode())));
        booking.setOwner(owner);
        booking.setApartment(apartment);
        booking.setClient(client);
        booking.setCurrency(currency);
        booking.setStatus(Status.NEW);
        bookingRepository.save(booking);
        boardDetailService.createBoardOfDetail(booking);
        return bookingMapper.mapToDto(booking);
    }

    @Override
    @Transactional
    public BookingDto findById(String id) {
        return bookingMapper.mapToDto(bookingRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new BookingNotFoundException(String.format("Booking with id %s not found", id))));
    }
}
