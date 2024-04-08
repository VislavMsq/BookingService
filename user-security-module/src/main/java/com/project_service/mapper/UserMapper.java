package com.project_service.mapper;

import com.project_service.dto.UserDto;
import com.project_service.entity.User;
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
