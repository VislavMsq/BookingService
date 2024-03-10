package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ApartmentDTO createApartment(@RequestBody ApartmentDTO apartmentDTO) {
        return apartmentService.createApartment(apartmentDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDTO findApartment(@PathVariable String id) {
        return apartmentService.findApartment(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDTO> findAllApartments() {
        return apartmentService.findAllApartments();
    }

    @GetMapping("/country/{country}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDTO> findApartmentsByCountry(@PathVariable String country) {
        return apartmentService.findApartmentByCountry(country);
    }

    @GetMapping("/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDTO> findApartmentsByCity(@PathVariable String city) {
        return apartmentService.findApartmentByCity(city);
    }

    @PostMapping("/set-apartment-category/{apartmentCategoryId}")
    @PreAuthorize("hasAuthority('OWNER')")
    @ResponseStatus(HttpStatus.OK)
    public void setApartmentsCategory(@RequestBody List<String> apartmentIds, @PathVariable String apartmentCategoryId) {
        apartmentService.setApartmentCategoryToApartments(apartmentIds, apartmentCategoryId);
    }
}
