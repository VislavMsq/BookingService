package com.project_service.service.impl;


import com.project_service.dto.CurrencyDto;
import com.project_service.entity.Currency;
import com.project_service.mapper.CurrencyMapper;
import com.project_service.repository.CurrencyRepository;
import com.project_service.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    public List<CurrencyDto> getAll() {
        List<Currency> currency = currencyRepository.findAll();
        return currencyMapper.currencyToCurrencyDto(currency);

    }
}
