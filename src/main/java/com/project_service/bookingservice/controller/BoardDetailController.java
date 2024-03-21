package com.project_service.bookingservice.controller;

import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.dto.BoardDetailsFilterDto;
import com.project_service.bookingservice.service.BoardDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/board-details")
public class BoardDetailController {
    private final BoardDetailService boardDetailService;

    @GetMapping
    public List<BoardDetailDto> findAllBoardDetailDto(@RequestBody BoardDetailsFilterDto boardDetailsFilterDto) {
        return boardDetailService.findAllBoardDetailDto(boardDetailsFilterDto);
    }
}
