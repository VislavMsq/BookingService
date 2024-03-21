package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.ApartmentCategoryDto;
import com.project_service.bookingservice.service.ApartmentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartment-categories")
@RequiredArgsConstructor
public class ApartmentCategoryController {
    private final ApartmentCategoryService apartmentCategoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentCategoryDto createDTO(@RequestBody ApartmentCategoryDto dto) {
        return apartmentCategoryService.createApartmentCategory(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentCategoryDto findCategory(@PathVariable String id) {
        return apartmentCategoryService.getDTO(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentCategoryDto> findAllCategories() {
        return apartmentCategoryService.getList();
    }

}
