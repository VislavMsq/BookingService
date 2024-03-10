package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.CurrencyDto;
import com.project_service.bookingservice.entity.Currency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto currencyToCurrencyDto(Currency currency);

    List<CurrencyDto> currencyToCurrencyDto(List<Currency> currencyList);

}
