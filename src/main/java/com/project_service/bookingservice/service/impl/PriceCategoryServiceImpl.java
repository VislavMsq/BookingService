package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.PriceCategoryRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryService;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PriceCategoryServiceImpl implements PriceCategoryService {

    private final CurrencyRepository currencyRepository;
    private final PriceCategoryRepository priceCategoryRepository;
    private final PriceScheduleService priceScheduleService;
    private final PriceCategoryMapper priceCategoryMapper;
    private final UserProvider userProvider;


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto) {
        PriceCategory priceCategory = priceCategoryMapper.mapToEntity(priceCategoryDto);
        Currency currency = currencyRepository.findByCode(priceCategoryDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with code %s not found",
                        priceCategoryDto.getCurrencyCode())));
        priceCategory.setCurrency(currency);
        priceCategory.setOwner(userProvider.getCurrentUser());
        priceCategoryRepository.save(priceCategory);

        return priceScheduleService.createPriceSchedule(priceCategory, priceCategoryDto.getPeriods());
    }

    @Override
    @Transactional
    public PriceCategoryDto findById(String id) {
        return priceCategoryMapper.mapToDto(priceCategoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PriceCategoryNotFoundException(String.format("Price category with id %s not found",
                        id))));
    }
}
