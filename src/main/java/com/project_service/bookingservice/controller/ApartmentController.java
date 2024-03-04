package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.ApartmentDTO;
import com.project_service.bookingservice.dto.CreateApartmentDTO;
import com.project_service.bookingservice.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService apartmentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDTO createApartment(@RequestBody CreateApartmentDTO apartmentDTO){
        return apartmentService.createApartment(apartmentDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApartmentDTO findApartment(@PathVariable String id){
        return apartmentService.findApartment(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ApartmentDTO> findAllApartments(){
        return apartmentService.findAllApartments();
    }
}
