package com.project_servise.bookingservice.controller;

import com.project_servise.bookingservice.dto.ApartmentCategoryCreateDTO;
import com.project_servise.bookingservice.dto.ApartmentCategoryDTO;
import com.project_servise.bookingservice.service.ApartmentCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments_category")
@RequiredArgsConstructor
public class ApartmentCategoryController {
    private final ApartmentCategoryService apartmentCategoryService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentCategoryDTO createDTO(@RequestBody ApartmentCategoryCreateDTO dto){
        return apartmentCategoryService.createApartmentCategory(dto);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentCategoryDTO findCategory(@PathVariable String id){
        return apartmentCategoryService.getDTO(id);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentCategoryDTO> findAllCategories(){
        return apartmentCategoryService.getList();
    }
}
