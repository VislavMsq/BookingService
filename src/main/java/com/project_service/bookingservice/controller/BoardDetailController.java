package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.BoardDetailsOfRangeDto;
import com.project_service.bookingservice.service.BoardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/board_details")
public class BoardDetailController {
    private final BoardDetailService boardDetailService;

    @GetMapping("/find_all")
    public BoardDetailsOfRangeDto findAllBoardDetailDto(@RequestBody BoardDetailsOfRangeDto boardDetailsOfRangeDto) {
        return boardDetailService.findAllBoardDetailDto(boardDetailsOfRangeDto);
    }
}
