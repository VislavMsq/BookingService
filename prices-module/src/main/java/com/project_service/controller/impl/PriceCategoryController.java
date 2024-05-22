package com.project_service.controller.impl;

import com.project_service.controller.PriceCategoryOperationsService;
import com.project_service.dto.PriceCategoryDto;
import com.project_service.service.PriceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/price-category")
public class PriceCategoryController implements PriceCategoryOperationsService {
    private final PriceCategoryService priceCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto createPriceOfCategory(@RequestBody @Valid PriceCategoryDto priceCategoryDto) {
        return priceCategoryService.createPriceOfCategory(priceCategoryDto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto findById(@PathVariable("id") String id) {
        return priceCategoryService.findById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('OWNER')")
    public List<PriceCategoryDto> findAll() {
        return priceCategoryService.findAll();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('OWNER')")
    public PriceCategoryDto update(@RequestBody PriceCategoryDto priceCategoryDto) {
        return priceCategoryService.update(priceCategoryDto);
    }
}
