package com.project_service.mapper;

import com.project_service.dto.BoardDetailDto;
import com.project_service.entity.BoardDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardDetailMapper {
    @Mapping(source = "booking.id", target = "bookingId")
    @Mapping(source = "apartment.id", target = "apartmentId")
    @Mapping(source = "currency.code", target = "currencyCode")
    BoardDetailDto mapToDto(BoardDetail boardDetail);

    List<BoardDetailDto> toList(List<BoardDetail> boardDetailList);
}
