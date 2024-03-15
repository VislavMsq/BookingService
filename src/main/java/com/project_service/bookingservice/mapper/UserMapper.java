package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.UserDto;
import com.project_service.bookingservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    User mapToEntity(UserDto userDto);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "currency.code", target = "currencyCode")
    UserDto mapToDto(User user);
}
