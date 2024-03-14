package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.CategoryPriceSchedule;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.entity.PriceCategory;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.CurrencyRepository;
import com.project_service.bookingservice.repository.PriceCategoryRepository;
import com.project_service.bookingservice.repository.PriceScheduleRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryService;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class PriceCategoryServiceImpl implements PriceCategoryService {

    private final CurrencyRepository currencyRepository;
    private final PriceCategoryRepository priceCategoryRepository;
    private final PriceCategoryMapper priceCategoryMapper;
    private final PriceScheduleRepository priceScheduleRepository;
    private final PriceScheduleService priceScheduleService;
    private final PriceScheduleMapper priceScheduleMapper;
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
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto findById(String id) {
        PriceCategory priceCategory = priceCategoryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PriceCategoryNotFoundException(
                                String.format("Price category with id %s not found", id)
                        )
                );
        PriceCategoryDto priceCategoryDto = priceCategoryMapper.mapToDto(priceCategory);

        List<CategoryPriceSchedule> categoryPriceSchedules = priceScheduleRepository.
                findAllByPriceCategoryAndOwner(priceCategory, userProvider.getCurrentUser());

        priceCategoryDto.setPeriods(priceScheduleMapper.toScheduleDto(categoryPriceSchedules));

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

        List<CategoryPriceSchedule> categoryPriceSchedules = priceScheduleRepository.
                findAllByPriceCategoryAndOwner(priceCategory, owner);

        Map<ScheduleDto, CategoryPriceSchedule> map = new HashMap<>();

        categoryPriceSchedules
                .forEach(c -> map.put(priceScheduleMapper.toScheduleDto(c), c));

        List<CategoryPriceSchedule> saveList = new ArrayList<>();

        Map<ScheduleDto, CategoryPriceSchedule> mapForRemove = new HashMap<>(map);

        for (ScheduleDto scheduleDto : priceCategoryDto.getPeriods()) {
            if ((map.get(scheduleDto)) == null) {
                saveList.add(priceScheduleMapper.toEntity(priceCategory, owner, scheduleDto));
            } else {
                mapForRemove.remove(scheduleDto);
            }
        }

        priceScheduleRepository.deleteAll(mapForRemove.values());
        priceScheduleRepository.saveAll(saveList);

        categoryPriceSchedules = priceScheduleRepository.
                findAllByPriceCategoryAndOwner(priceCategory, owner);

        PriceCategoryDto result = priceCategoryMapper.mapToDto(priceCategory);

        result.setPeriods(priceScheduleMapper.toScheduleDto(categoryPriceSchedules));

        return result;
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
