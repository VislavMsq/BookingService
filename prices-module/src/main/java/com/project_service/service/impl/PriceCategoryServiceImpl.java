package com.project_service.service.impl;


import com.project_service.dto.PriceCategoryDto;
import com.project_service.entity.Currency;
import com.project_service.entity.PriceCategory;
import com.project_service.entity.PriceCategorySchedule;
import com.project_service.entity.User;
import com.project_service.exception.CurrencyNotFoundException;
import com.project_service.exception.PriceCategoryNotFoundException;
import com.project_service.mapper.PriceCategoryMapper;
import com.project_service.mapper.PriceScheduleMapper;
import com.project_service.repository.CurrencyRepository;
import com.project_service.repository.PriceCategoryRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.PriceCategoryService;
import com.project_service.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
    public PriceCategoryDto createPriceOfCategory(PriceCategoryDto priceCategoryDto) {
        User owner = userProvider.getCurrentUser();
        PriceCategory priceCategory = priceCategoryMapper.mapToEntity(priceCategoryDto);
        Currency currency = currencyRepository.findByCode(priceCategoryDto.getCurrencyCode())
                .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with code %s not found",
                        priceCategoryDto.getCurrencyCode())));
        priceCategory.setCurrency(currency);
        priceCategory.setOwner(owner);
        priceCategory.getPriceCategoryScheduleSet()
                .forEach(c -> c.setPriceCategory(priceCategory));
        priceCategoryRepository.save(priceCategory);
        return priceCategoryMapper.mapToDto(priceCategory);
    }

    @Override
    @Transactional
    public PriceCategoryDto findById(String id) {
        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PriceCategoryNotFoundException(
                                String.format("Price category with id %s not found", id)
                        )
                );
        PriceCategoryDto priceCategoryDto = priceCategoryMapper.mapToDto(priceCategory);
        List<PriceCategorySchedule> priceCategorySchedules = priceCategory.getPriceCategoryScheduleSet().stream().toList();
        priceCategoryDto.setPeriods(priceScheduleMapper.toScheduleDto(priceCategorySchedules));
        return priceCategoryDto;
    }

    @Override
    @Transactional
    public PriceCategoryDto update(PriceCategoryDto priceCategoryDto) {
        User owner = userProvider.getCurrentUser();
        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(priceCategoryDto.getId()))
                .orElseThrow(() -> new PriceCategoryNotFoundException(
                                String.format("Price category with id %s not found", priceCategoryDto.getId())
                        )
                );
        UtilsService.checkOwner(priceCategory, owner);
        priceCategory.setCurrency(
                currencyRepository.findByCode(priceCategoryDto.getCurrencyCode())
                        .orElseThrow(() -> new CurrencyNotFoundException(String.format("Currency with code %s not found",
                                priceCategoryDto.getCurrencyCode()))));
        priceCategoryMapper.update(priceCategoryDto, priceCategory);
        priceCategory.getPriceCategoryScheduleSet()
                .forEach(c -> c.setPriceCategory(priceCategory));
        priceCategoryRepository.save(priceCategory);
        return priceCategoryMapper.mapToDto(priceCategory);
    }

    @Override
    @Transactional
    public List<PriceCategoryDto> findAll() {
        return priceCategoryMapper.mapToListDto(priceCategoryRepository.findAllByOwner(userProvider.getCurrentUser()));
    }
}
