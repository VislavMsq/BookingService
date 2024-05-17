package com.project_service.service;

import com.project_service.dto.CurrencyDto;
import com.project_service.entity.Currency;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getAll();
    Currency findByCode(String code);
}
