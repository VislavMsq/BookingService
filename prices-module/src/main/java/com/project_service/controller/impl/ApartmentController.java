package com.project_service.controller.impl;

import com.project_service.controller.ApartmentOperationsService;
import com.project_service.dto.ApartmentDto;
import com.project_service.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController implements ApartmentOperationsService {
    private final ApartmentService apartmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDto createApartment(@RequestBody ApartmentDto apartmentDTO) {
        return apartmentService.createApartment(apartmentDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDto findApartment(@PathVariable String id) {
        return apartmentService.findApartment(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDto> findAllApartments() {
        return apartmentService.findAllApartments();
    }

    @GetMapping("/country/{country}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDto> findApartmentsByCountry(@PathVariable String country) {
        return apartmentService.findApartmentByCountry(country);
    }

    @GetMapping("/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDto> findApartmentsByCity(@PathVariable String city) {
        return apartmentService.findApartmentByCity(city);
    }

    @PutMapping("/set-apartment-category/{apartmentCategoryId}")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    public void setApartmentsCategory(@RequestBody List<String> apartmentIds, @PathVariable String apartmentCategoryId) {
        apartmentService.setApartmentCategoryToApartments(apartmentIds, apartmentCategoryId);
    }
}
