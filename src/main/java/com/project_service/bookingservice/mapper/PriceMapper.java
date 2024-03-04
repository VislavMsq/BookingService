package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.PriceDto;
import com.project_service.bookingservice.entity.Price;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    @Named("toPriceDto")
    @Mapping(source = "pricePerDay", target = "price")
    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "currency.name", target = "currencyName")
    @Mapping(source = "currency.currencyCode", target = "currencyCode")
    PriceDto mapToDto(Price price);

    @IterableMapping(qualifiedByName = "toPriceDto")
    List<PriceDto> mapToListDtos(List<Price> pricesOfApartment);
}
