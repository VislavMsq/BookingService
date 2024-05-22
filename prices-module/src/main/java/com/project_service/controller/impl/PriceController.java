package com.project_service.controller.impl;

import com.project_service.controller.PriceOperationsService;
import com.project_service.dto.PriceDto;
import com.project_service.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/prices")
public class PriceController implements PriceOperationsService {

    private final PriceService priceService;

    @GetMapping("/apartment/{id}")
    public List<PriceDto> getPricesOfApartment(@PathVariable("id") String apartmentId,
                                               @RequestParam("startDate") LocalDate startDate,
                                               @RequestParam("endDate") LocalDate endDate) {
        return priceService.getPricesOfApartment(apartmentId, startDate, endDate);
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PutMapping
    public ResponseEntity<Void> updatePrices(@RequestParam("startDate") LocalDate startDate,
                                             @RequestParam("endDate") LocalDate endDate,
                                             @RequestBody List<String> apartmentIds) {
        priceService.updatePrices(apartmentIds, startDate, endDate);
        return ResponseEntity.ok().build();
    }
}
