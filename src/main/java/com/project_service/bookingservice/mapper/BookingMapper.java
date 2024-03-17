package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "currency.code", target = "currencyCode")
    BookingDto mapToDto(Booking booking);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    Booking mapToEntity(BookingDto bookingDto);
}
