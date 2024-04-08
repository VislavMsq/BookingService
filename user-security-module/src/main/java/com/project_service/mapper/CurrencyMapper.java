package com.project_service.mapper;

import com.project_service.dto.CurrencyDto;
import com.project_service.entity.Currency;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CurrencyMapper {

    CurrencyDto currencyToCurrencyDto(Currency currency);

    List<CurrencyDto> currencyToCurrencyDto(List<Currency> currencyList);

}
