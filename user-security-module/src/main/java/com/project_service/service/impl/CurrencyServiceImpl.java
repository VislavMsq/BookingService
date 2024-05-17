package com.project_service.service.impl;


import com.project_service.dto.CurrencyDto;
import com.project_service.entity.Currency;
import com.project_service.exception.CurrencyNotFoundException;
import com.project_service.mapper.CurrencyMapper;
import com.project_service.repository.CurrencyRepository;
import com.project_service.service.CurrencyService;
import jakarta.transaction.Transactional;
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

    @Override
    @Transactional
    public Currency findByCode(String code) {
        return currencyRepository.findByCode(code)
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency code %s not found",
                        code)));
    }
}
