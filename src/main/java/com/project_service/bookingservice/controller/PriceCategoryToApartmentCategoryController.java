package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.bookingservice.service.PriceCategoryToApartmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/prices-to-apartments")
public class PriceCategoryToApartmentCategoryController {

    private final PriceCategoryToApartmentCategoryService priceCategoryToApartmentCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceCategoryToApartmentCategoryDto create(@RequestBody PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto) {
        return priceCategoryToApartmentCategoryService.create(priceCategoryToApartmentCategoryDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceCategoryToApartmentCategoryDto findPriceToApartment(@PathVariable String id) {
        return priceCategoryToApartmentCategoryService.getDto(id);
    }
}
