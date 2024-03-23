package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.BoardDetailDto;
import com.project_service.bookingservice.entity.BoardDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardDetailMapper {
    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "currency.code", target = "currencyCode")
    BoardDetailDto mapToDto(BoardDetail boardDetail);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    BoardDetail mapToEntity (BoardDetailDto boardDetailDto);

    List<BoardDetailDto> toList(List<BoardDetail> boardDetailList);
}
