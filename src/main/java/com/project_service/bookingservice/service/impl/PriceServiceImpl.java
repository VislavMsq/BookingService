package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.dto.BookingDto;
import com.project_service.bookingservice.dto.PriceDto;
import com.project_service.bookingservice.entity.*;
import com.project_service.bookingservice.mapper.PriceMapper;
import com.project_service.bookingservice.repository.ApartmentRepository;
import com.project_service.bookingservice.repository.PriceRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {
    private final PriceRepository priceRepository;
    private final ApartmentRepository apartmentRepository;
    private final PriceMapper priceMapper;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public List<PriceDto> getPricesOfApartment(BookingDto bookingDto) {
        List<Price> pricesOfApartment = priceRepository.findPricesOfApartment(
                UUID.fromString(bookingDto.getApartmentId()), bookingDto.getStartDate(), bookingDto.getEndDate()
        );
        return priceMapper.mapToListDtos(pricesOfApartment);
    }

    @Override
    @Transactional
    public void updatePrices(BoardDetailsFilterDto boardDetailsFilterDto) {
        User owner = userProvider.getCurrentUser();
        List<String> apartmentIds = boardDetailsFilterDto.getApartmentIds();
        LocalDate startDate = boardDetailsFilterDto.getStartDate();
        LocalDate endDate = boardDetailsFilterDto.getEndDate();
        List<Apartment> apartments = getApartments(apartmentIds, owner, startDate, endDate);
        List<Price> prices = priceRepository.findPricesOfListApartments(apartmentIds, startDate, endDate);
        Map<Pair<Apartment, LocalDate>, Price> priceOfApartmentsByDay = prices.stream()
                .collect(Collectors.toMap(p -> Pair.of(p.getApartment(), p.getDate()), p -> p));
        List<Price> pricesToSave = new ArrayList<>(prices.size());
        fillPricesToSave(startDate, endDate, apartments, priceOfApartmentsByDay, pricesToSave);
        priceRepository.saveAll(pricesToSave);
    }

    private List<Apartment> getApartments(List<String> apartmentIds, User owner, LocalDate startDate, LocalDate endDate) {
        return (apartmentIds == null || apartmentIds.isEmpty()) ?
                apartmentRepository.findAllByOwnerWithPrices(owner, startDate.getYear(),
                        startDate.getMonthValue(), startDate.getDayOfMonth(),
                        endDate.getMonthValue(), endDate.getDayOfMonth()) :
                apartmentRepository.findAllByIdAndOwnerWithPrices(apartmentIds, owner, startDate.getYear(),
                        startDate.getMonthValue(), startDate.getDayOfMonth(),
                        endDate.getMonthValue(), endDate.getDayOfMonth());
    }

    private void fillPricesToSave(LocalDate startDate, LocalDate endDate, List<Apartment> apartments,
                                  Map<Pair<Apartment, LocalDate>, Price> priceOfApartmentsByDay,
                                  List<Price> pricesToSave) {
        long dateRange = startDate.until(endDate, ChronoUnit.DAYS);
        for (int i = 0; i <= dateRange; i++) {
            LocalDate date = startDate.plusDays(i);
            apartments.forEach(apartment -> {
                Price price = priceOfApartmentsByDay.get(Pair.of(apartment, date));
                if (price == null) {
                    getPricePerDay(apartment, date).ifPresent(pricesToSave::add);
                } else {
                    updatePricePerDay(price, apartment, date);
                    pricesToSave.add(price);
                }
            });
        }
    }

    private Optional<Price> getPricePerDay(Apartment apartment, LocalDate date) {
        Optional<PriceCategoryToApartmentCategory> categoryToCategoryOptional =
                getCategoryToCategoryWithMaxPriority(apartment, date);
        if (categoryToCategoryOptional.isEmpty()) {
            return Optional.empty();
        }
        PriceCategoryToApartmentCategory categoryToCategory = categoryToCategoryOptional.get();
        Price price = new Price();
        price.setApartment(apartment);
        price.setCurrency(categoryToCategory.getCurrency());
        price.setPricePerDay(categoryToCategory.getPrice());
        price.setPriority(categoryToCategory.getPriceCategory().getPriority());
        price.setDate(date);
        price.setIsEditedPrice(false);
        price.setOwner(apartment.getOwner());
        price.setIsDeleted(false);
        price.setCreatedAt(LocalDateTime.now());
        price.setUpdatedAt(LocalDateTime.now());
        return Optional.of(price);
    }

    private Optional<PriceCategoryToApartmentCategory> getCategoryToCategoryWithMaxPriority(Apartment apartment, LocalDate date) {
        return apartment.getApartmentCategory()
                .getCategoryToCategorySet().stream()
                .filter(ctc -> ctc.getPeriod().equals(date.getYear())
                        && ctc.getPriceCategory().getPriceCategoryScheduleSet().stream()
                        .anyMatch(schedule -> isDateInRange(date, schedule)))
                .max(Comparator.comparing(ctc -> ctc.getPriceCategory().getPriority().getValue()));
    }

    private void updatePricePerDay(Price price, Apartment apartment, LocalDate date) {
        Optional<PriceCategoryToApartmentCategory> optionalCTC =
                getCategoryToCategoryWithMaxPriority(apartment, date);
        if (optionalCTC.isPresent()) {
            PriceCategoryToApartmentCategory categoryToCategory = optionalCTC.get();
            price.setPricePerDay(categoryToCategory.getPrice());
            price.setCurrency(categoryToCategory.getCurrency());
            price.setPriority(categoryToCategory.getPriceCategory().getPriority());
        } else {
            price.setPricePerDay(BigDecimal.ZERO);
            price.setPriority(null);
        }
        price.setIsEditedPrice(false);
        price.setUpdatedAt(LocalDateTime.now());
    }

    private boolean isDateInRange(LocalDate date, PriceCategorySchedule schedule) {
        MonthDay monthDay = MonthDay.of(date.getMonthValue(), date.getDayOfMonth());
        MonthDay startDate = MonthDay.of(schedule.getStartMonth(), schedule.getStartDay());
        MonthDay endDate = MonthDay.of(schedule.getEndMonth(), schedule.getEndDay());
        return !monthDay.isBefore(startDate) && !monthDay.isAfter(endDate);
    }
}
