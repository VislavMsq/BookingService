package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.dto.ScheduleDto;
import com.project_service.bookingservice.entity.*;
import com.project_service.bookingservice.entity.Currency;
import com.project_service.bookingservice.exception.CurrencyNotFoundException;
import com.project_service.bookingservice.exception.EmptyDatesException;
import com.project_service.bookingservice.exception.PriceCategoryNotFoundException;
import com.project_service.bookingservice.mapper.PriceCategoryMapper;
import com.project_service.bookingservice.mapper.PriceScheduleMapper;
import com.project_service.bookingservice.repository.*;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceCategoryService;
import com.project_service.bookingservice.service.PriceScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.time.temporal.ChronoUnit;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class PriceCategoryServiceImpl implements PriceCategoryService {

    private final PriceCategoryToApartmentCategoryRepository categoryToCategory;
    private final CurrencyRepository currencyRepository;
    private final PriceRepository priceRepository;
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

        if (priceCategoryDto.getPeriods().isEmpty()) {
            throw new EmptyDatesException("No submitted date range");
        }

        Year year = Year.of(priceCategoryDto.getPeriods().get(0).getStartDate().getYear());

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

        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        scheduleDtoList.addAll(map.keySet().stream().toList());
        scheduleDtoList.addAll(priceCategoryDto.getPeriods());

        List<LocalDate> periodList =  scheduleDtoList
                .stream()
                .map(s -> {
                            LocalDate startDate = s.getStartDate();
                            long numOfDays = ChronoUnit.DAYS.between(s.getStartDate(), s.getEndDate()) + 1;
                            return IntStream.iterate(0, i -> i + 1)
                                    .limit(numOfDays)
                                    .mapToObj(startDate::plusDays)
                                    .collect(Collectors.toList());
                        }
                )
                .flatMap(List::stream)
                .collect(Collectors.toSet()).stream().sorted().toList();

        List<PriceCategoryToApartmentCategory> categoryToCategoryList = categoryToCategory.findAllByOwnerAndPeriod(owner, year);

        List<Price> priceList = priceRepository.findPricesByApartmentsAndYearAndBetweenDates(
                categoryToCategoryList
                        .stream()
                        .map(c -> c.getApartmentCategory().getApartmentList())
                        .flatMap(Set::stream).toList(),
                periodList.getFirst(),
                periodList.getLast(),
                owner).stream().filter(p -> p.getDate().getYear() == year.getValue()).toList();

        priceRepository.deleteAll(priceList);

        List<Price> prices = categoryToCategoryList.stream()
                .flatMap(c2c -> c2c.getApartmentCategory().getApartmentList().stream()
                        .flatMap(apartment -> c2c.getPriceCategory().getCategoryPriceScheduleList().stream()
                                .flatMap(schedule -> periodList.stream()
                                        .filter(date -> !date.isBefore(schedule.getStartDate().toLocalDate()) && !date.isAfter(schedule.getEndDate().toLocalDate()))
                                        .map(date -> {
                                            Price price = new Price();
                                            price.setOwner(owner);
                                            price.setApartment(apartment);
                                            price.setCurrency(c2c.getCurrency());
                                            price.setPricePerDay(c2c.getPrice());
                                            price.setDate(date);
                                            price.setIsEditedPrice(false);
                                            price.setPriority(c2c.getPriceCategory().getPriority());
                                            price.setIsDeleted(false);
                                            price.setCreatedAt(LocalDateTime.now());
                                            price.setUpdatedAt(LocalDateTime.now());
                                            return price;
                                        }))))
                .toList();

        prices = prices.stream()
                .collect(Collectors.groupingBy(Price::getDate))
                .values().stream()
                .map(p -> p.stream()
                        .max(Comparator.comparing(price -> price.getPriority().ordinal()))
                        .orElseThrow(() -> new RuntimeException("Unexpected error")))
                .collect(Collectors.toList());

        priceRepository.saveAll(prices);

        priceCategoryRepository.save(priceCategory);

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
