package com.project_service.service.impl;


import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.entity.*;
import com.project_service.exception.ApartmentCategoryNotFoundException;
import com.project_service.exception.CurrencyNotFoundException;
import com.project_service.exception.PriceCategoryNotFoundException;
import com.project_service.mapper.PriceCategoryToApartmentCategoryMapper;
import com.project_service.repository.ApartmentCategoryRepository;
import com.project_service.repository.CurrencyRepository;
import com.project_service.repository.PriceCategoryRepository;
import com.project_service.repository.PriceCategoryToApartmentCategoryRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.PriceCategoryToApartmentCategoryService;
import com.project_service.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PriceCategoryToApartmentCategoryServiceImpl implements PriceCategoryToApartmentCategoryService {
    private final PriceCategoryToApartmentCategoryRepository priceCategoryToApartmentCategoryRepository;
    private final ApartmentCategoryRepository apartmentCategoryRepository;
    private final PriceCategoryRepository priceCategoryRepository;
    private final CurrencyRepository currencyRepository;
    private final PriceCategoryToApartmentCategoryMapper categoryToCategoryMapper;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto categoryToCategoryDto) {
        PriceCategoryToApartmentCategory priceCategoryToApartmentCategory = categoryToCategoryMapper
                .mapToEntity(categoryToCategoryDto);
        User owner = userProvider.getCurrentUser();
        ApartmentCategory apartmentCategory = apartmentCategoryRepository.findById(UUID.fromString(
                        categoryToCategoryDto.getApartmentCategoryId()))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException(String.format("Apartment category with id %s not found",
                        categoryToCategoryDto.getApartmentCategoryId())));

        UtilsService.checkOwner(apartmentCategory, owner);

        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(
                        categoryToCategoryDto.getPriceCategoryId()))
                .orElseThrow(() -> new PriceCategoryNotFoundException(String.format("Price category %s not found",
                        categoryToCategoryDto.getPriceCategoryId())));

        UtilsService.checkOwner(priceCategory, owner);

        Currency currency = currencyRepository.findByCode(categoryToCategoryDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency code %s not found",
                        categoryToCategoryDto.getCurrencyCode())));
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
        return priceCategoryToApartmentCategoryRepository.findById(UUID.fromString(uuid))
                .map(categoryToCategoryMapper::mapToDto)
                .orElseThrow(() -> new PriceCategoryNotFoundException(String.format("Price category %s not found", uuid)));
    }
}

