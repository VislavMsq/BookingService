package com.project_service.controller;

import com.project_service.dto.BoardDetailDto;
import com.project_service.service.BoardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/board-details")
public class BoardDetailController {
    private final BoardDetailService boardDetailService;

    @GetMapping
    public List<BoardDetailDto> findAllBoardDetailDto(@RequestParam("startDate") LocalDate startDate,
                                                      @RequestParam("endDate") LocalDate endDate) {
        return boardDetailService.findAllBoardDetailDto(startDate, endDate);
    }
}
