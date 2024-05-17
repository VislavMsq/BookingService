package com.project_service.controller;

import com.project_service.dto.PriceCategoryToApartmentCategoryDto;
import com.project_service.service.PriceCategoryToApartmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryToApartmentCategoryDto update(@RequestBody PriceCategoryToApartmentCategoryDto priceCategoryToApartmentCategoryDto) {
        return priceCategoryToApartmentCategoryService.update(priceCategoryToApartmentCategoryDto);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceCategoryToApartmentCategoryDto findPriceToApartment(@PathVariable String id) {
        return priceCategoryToApartmentCategoryService.getDto(id);
    }
}
