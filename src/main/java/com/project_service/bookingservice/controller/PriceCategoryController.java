package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.PriceCategoryDto;
import com.project_service.bookingservice.service.PriceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/price-category")
public class PriceCategoryController {
    private final PriceCategoryService priceCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PriceCategoryDto createPriceOfCategory(@RequestBody PriceCategoryDto priceCategoryDto) {
        return priceCategoryService.createPriceOfCategory(priceCategoryDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PriceCategoryDto findById(@PathVariable("id") String id) {
        return priceCategoryService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PriceCategoryDto> findAll() {
        return priceCategoryService.findAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public PriceCategoryDto update(@RequestBody PriceCategoryDto priceCategoryDto) {
        return priceCategoryService.update(priceCategoryDto);
    }
}
