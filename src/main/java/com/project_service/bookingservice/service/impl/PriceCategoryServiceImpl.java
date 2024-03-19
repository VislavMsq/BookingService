package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.*;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.*;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryService;
import com.project_service.bookingservice.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PriceCategoryServiceImpl implements PriceCategoryService {

    private final CurrencyRepository currencyRepository;
    private final PriceCategoryRepository priceCategoryRepository;
    private final PriceCategoryMapper priceCategoryMapper;
    private final PriceScheduleMapper priceScheduleMapper;
    private final UserProvider userProvider;


    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto) {
        User owner = userProvider.getCurrentUser();
        PriceCategory priceCategory = priceCategoryMapper.mapToEntity(priceCategoryDto);
        Currency currency = currencyRepository.findByCode(priceCategoryDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with code %s not found",
                        priceCategoryDto.getCurrencyCode())));
        priceCategory.setCurrency(currency);
        priceCategory.setOwner(owner);
        priceCategory.getPriceCategoryScheduleList().stream()
                .peek(c -> c.setOwner(owner))
                .forEach(c -> c.setPriceCategory(priceCategory));
        priceCategoryRepository.save(priceCategory);

        return priceCategoryMapper.mapToDto(priceCategory);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto findById(String id) {
        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PriceCategoryNotFoundException(
                                String.format("Price category with id %s not found", id)
                        )
                );
        PriceCategoryDto priceCategoryDto = priceCategoryMapper.mapToDto(priceCategory);

        List<PriceCategorySchedule> priceCategorySchedules = priceCategory.getPriceCategoryScheduleList().stream().toList();

        priceCategoryDto.setPeriods(priceScheduleMapper.toScheduleDto(priceCategorySchedules));

        return priceCategoryDto;
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto update(PriceCategoryDto priceCategoryDto) {

        User owner = userProvider.getCurrentUser();

        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(priceCategoryDto.getId()))
                .orElseThrow(() -> new PriceCategoryNotFoundException(
                                String.format("Price category with id %s not found", priceCategoryDto.getId())
                        )
                );

        UtilsService.checkOwner(priceCategory, owner);

        priceCategoryMapper.update(priceCategoryDto, priceCategory);

        priceCategory.getPriceCategoryScheduleList().stream()
                .peek(c -> c.setPriceCategory(priceCategory))
                .forEach(c -> c.setOwner(owner));

        priceCategoryRepository.save(priceCategory);

        return priceCategoryMapper.mapToDto(priceCategory);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public List<PriceCategoryDto> findAll() {

        List<PriceCategory> priceCategoryList = priceCategoryRepository.findAllByOwner(userProvider.getCurrentUser());

        return priceCategoryList.stream()
                .map(p -> findById(p.getId().toString()))
                .toList();
    }
}
