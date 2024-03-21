package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.PriceCategorySchedule;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.PriceCategoryRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryService;
import com.project_service.bookingservice.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('OWNER')")
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
    @PreAuthorize("hasAuthority('OWNER')")
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
    @PreAuthorize("hasAuthority('OWNER')")
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
    @PreAuthorize("hasAuthority('OWNER')")
    public List<PriceCategoryDto> findAll() {
        return priceCategoryMapper.mapToListDto(priceCategoryRepository.findAllByOwner(userProvider.getCurrentUser()));
    }
}
