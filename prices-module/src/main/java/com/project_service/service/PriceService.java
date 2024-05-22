package com.project_service.service;

import com.project_service.dto.PriceDto;

import java.time.LocalDate;
import java.util.List;

public interface PriceService {

    List<PriceDto> getPricesOfApartment(String apartmentId, LocalDate startDate, LocalDate endDate);

    void updatePrices(List<String> apartmentIds, LocalDate startDate, LocalDate endDate);

}


