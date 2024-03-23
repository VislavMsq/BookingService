package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.bookingservice.entity.*;
import com.project_service.bookingservice.exception.ApartmentCategoryNotFoundException;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryToApartmentCategoryMapper;
import com.project_service.bookingservice.repository.ApartmentCategoryRepository;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.PriceCategoryRepository;
import com.project_service.bookingservice.repository.PriceCategoryToApartmentCategoryRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryToApartmentCategoryService;
import com.project_service.bookingservice.service.UtilsService;
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
    public PriceCategoryToApartmentCategoryDto create(PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto) {
        PriceCategoryToApartmentCategory priceCategoryToApartmentCategory = categoryToCategoryMapper
                .mapToEntity(priceCategoryToApartmentCategoryDto);
        User owner = userProvider.getCurrentUser();
        ApartmentCategory apartmentCategory = apartmentCategoryRepository.findById(
                        UUID.fromString(priceCategoryToApartmentCategoryDto.getApartmentCategoryId()))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException(String.format("Apartment category with id %s not found",
                        priceCategoryToApartmentCategoryDto.getApartmentCategoryId())));

        UtilsService.checkOwner(apartmentCategory, owner);

        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(
                        priceCategoryToApartmentCategoryDto.getPriceCategoryId()))
                .orElseThrow(() -> new PriceCategoryNotFoundException(String.format("Price category %s not found",
                        priceCategoryToApartmentCategoryDto.getPriceCategoryId())));

        UtilsService.checkOwner(priceCategory, owner);

        Currency currency = currencyRepository.findByCode(priceCategoryToApartmentCategoryDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency code %s not found",
                        priceCategoryToApartmentCategoryDto.getCurrencyCode())));
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

