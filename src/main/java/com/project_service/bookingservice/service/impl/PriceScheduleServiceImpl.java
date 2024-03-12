package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.PriceScheduleRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceScheduleServiceImpl implements PriceScheduleService {
    private final PriceScheduleRepository priceScheduleRepository;
    private final PriceCategoryMapper priceCategoryMapper;
    private final UserProvider userProvider;
    private final PriceScheduleMapper priceScheduleMapper;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto createPriceSchedule(PriceCategory priceCategory, List<ScheduleDto> scheduleDtoList) {
        User owner = userProvider.getCurrentUser();

        PriceCategoryDto priceCategoryDto = priceCategoryMapper.mapToDto(priceCategory);

        List<CategoryPriceSchedule> categoryPriceScheduleList = new ArrayList<>();
        if (scheduleDtoList == null || scheduleDtoList.isEmpty()) {
            return priceCategoryDto;
        }
        for (ScheduleDto priceScheduleDto : scheduleDtoList) {

            CategoryPriceSchedule categoryPriceSchedule = priceScheduleMapper.toEntity(
                    priceCategory, owner, priceScheduleDto);

            categoryPriceScheduleList.add(categoryPriceSchedule);
        }
        priceScheduleRepository.saveAll(categoryPriceScheduleList);

        priceCategoryDto.setPeriods(priceScheduleMapper.toScheduleDto(categoryPriceScheduleList));

        return priceCategoryDto;
    }
}