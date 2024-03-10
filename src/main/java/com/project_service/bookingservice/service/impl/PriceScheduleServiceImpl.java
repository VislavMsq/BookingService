package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.PriceScheduleDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.entity.enums.Priority;
import com.project_service.bookingservice.exception.EntityNotFoundException;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.PriceCategoryRepository;
import com.project_service.bookingservice.repository.PriceScheduleRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PriceScheduleServiceImpl implements PriceScheduleService {
    private final PriceScheduleRepository priceScheduleRepository;
    private final PriceCategoryRepository priceCategoryRepository;
    private final CurrencyRepository currencyRepository;
    private final UserProvider userProvider;
    private final PriceScheduleMapper priceScheduleMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public void createPriceOfCategory(List<PriceCategoryDto> priceCategoryDtoList) {
        User owner = userProvider.getCurrentUser();

        List<PriceCategory> priceCategoryList = new ArrayList<>();
        List<CategoryPriceSchedule> categoryPriceScheduleList = new ArrayList<>();

        for (PriceCategoryDto priceCategoryDto : priceCategoryDtoList) {
            PriceCategory priceCategory = new PriceCategory();
            priceCategory.setOwner(owner);
            priceCategory.setCurrency(
                    currencyRepository.findById(UUID.fromString(priceCategoryDto.getCurrencyCode())).orElseThrow(
                            ()-> new EntityNotFoundException(String.format(
                                    "Currency with '%s' not found",
                                    priceCategoryDto.getCurrencyCode()))
                    )
            );
            priceCategory.setName(priceCategoryDto.getName());
            priceCategory.setPriority(Priority.valueOf(priceCategoryDto.getPriority()));
            priceCategory.setCreatedAt(LocalDateTime.now());
            priceCategory.setUpdatedAt(LocalDateTime.now());

            priceCategoryList.add(priceCategory);

            for (PriceScheduleDto priceScheduleDto : priceCategoryDto.getPeriods()) {
                CategoryPriceSchedule categoryPriceSchedule = new CategoryPriceSchedule();
                categoryPriceSchedule.setOwner(owner);
                categoryPriceSchedule.setPriceCategory(priceCategory);
                categoryPriceSchedule.setStartDate(priceScheduleDto.getStartDate().atStartOfDay());
                categoryPriceSchedule.setEndDate(priceScheduleDto.getEndDate().atStartOfDay());
                categoryPriceSchedule.setCreatedAt(LocalDateTime.now());
                categoryPriceSchedule.setUpdatedAt(LocalDateTime.now());

                categoryPriceScheduleList.add(categoryPriceSchedule);
            }
        }
        priceCategoryRepository.saveAll(priceCategoryList);
        priceScheduleRepository.saveAll(categoryPriceScheduleList);
    }

    @Override
    public List<ScheduleDto> findAll() {
        return priceScheduleMapper.toScheduleDto(priceScheduleRepository.findAllByOwner(userProvider.getCurrentUser()));
    }
}