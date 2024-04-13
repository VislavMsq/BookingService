package com.project_service.service.impl;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.entity.*;
import com.project_service.exception.ApartmentCategoryNotFoundException;
import com.project_service.exception.PriceCategoryNotFoundException;
import com.project_service.mapper.PriceCategoryToApartmentCategoryMapper;
import com.project_service.repository.ApartmentCategoryRepository;
import com.project_service.repository.CurrencyRepository;
import com.project_service.repository.PriceCategoryToApartmentCategoryRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.CurrencyService;
import com.project_service.service.PriceCategoryService;
import com.project_service.service.PriceCategoryToApartmentCategoryService;
import com.project_service.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PriceCategoryToApartmentCategoryServiceImpl implements PriceCategoryToApartmentCategoryService {
    private final PriceCategoryToApartmentCategoryRepository priceCategoryToApartmentCategoryRepository;
    private final ApartmentCategoryRepository apartmentCategoryRepository;
    private final CurrencyRepository currencyRepository;
    private final PriceCategoryToApartmentCategoryMapper categoryToCategoryMapper;
    private final UserProvider userProvider;
    private final PriceCategoryService priceCategoryService;
    private final CurrencyService currencyService;

    @Override
    @Transactional
    public PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto categoryToCategoryDto) {

        PriceCategoryToApartmentCategory priceCategoryToApartmentCategory = categoryToCategoryMapper
                .mapToEntity(categoryToCategoryDto);
        User owner = userProvider.getCurrentUser();
        ApartmentCategory apartmentCategory = apartmentCategoryRepository.findById(
                        UUID.fromString(categoryToCategoryDto.getApartmentCategoryId()))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException(String.format("Apartment category with id %s not found",
                        categoryToCategoryDto.getApartmentCategoryId())));

        UtilsService.checkOwner(apartmentCategory, owner);

        PriceCategory priceCategory = priceCategoryService.findPriceCategoryById(categoryToCategoryDto.getPriceCategoryId());

        UtilsService.checkOwner(priceCategory, owner);

        Currency currency;
        if (categoryToCategoryDto.getCurrencyCode() != null) {
            currency = currencyService.findByCode(categoryToCategoryDto.getCurrencyCode());
        } else {
            currency = priceCategory.getCurrency();
        }
        priceCategoryToApartmentCategory.setOwner(owner);
        priceCategoryToApartmentCategory.setApartmentCategory(apartmentCategory);
        priceCategoryToApartmentCategory.setPriceCategory(priceCategory);
        priceCategoryToApartmentCategory.setCurrency(currency);
        priceCategoryToApartmentCategoryRepository.save(priceCategoryToApartmentCategory);

        return categoryToCategoryMapper.mapToDto(priceCategoryToApartmentCategory);
    }

    @Override
    @Transactional
    public PriceCategoryToApartmentCategoryDto getDto(String uuid) {
        PriceCategoryToApartmentCategory priceCategoryToApartmentCategory = findById(uuid);
        return categoryToCategoryMapper.mapToDto(priceCategoryToApartmentCategory);
    }

    @Override
    @Transactional
    public PriceCategoryToApartmentCategoryDto update(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto) {

        User owner = userProvider.getCurrentUser();
        PriceCategoryToApartmentCategory priceCategoryToApartmentCategory = findById(priceCategoryToApartmentCategoryDto.getId());

        UtilsService.checkOwner(priceCategoryToApartmentCategory, owner);

        Currency currency = currencyService.findByCode(priceCategoryToApartmentCategoryDto.getCurrencyCode());

        priceCategoryToApartmentCategory.setCurrency(currency);
        priceCategoryToApartmentCategory.setPrice(BigDecimal.valueOf(priceCategoryToApartmentCategoryDto.getPrice()));
        priceCategoryToApartmentCategory.setUpdatedAt(LocalDateTime.now());

        priceCategoryToApartmentCategoryRepository.save(priceCategoryToApartmentCategory);

        return categoryToCategoryMapper.mapToDto(priceCategoryToApartmentCategory);
    }

    private PriceCategoryToApartmentCategory findById(String id) {
        return priceCategoryToApartmentCategoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PriceCategoryNotFoundException(String.format("Price category %s not found", id)));
    }
}

