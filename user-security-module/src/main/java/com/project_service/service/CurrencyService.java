package com.project_service.service;

import com.project_service.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getAll();
}
