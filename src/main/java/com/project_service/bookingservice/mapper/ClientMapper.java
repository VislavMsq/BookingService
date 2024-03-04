package com.project_service.bookingservice.mapper;

import com.project_service.bookingservice.dto.ClientDto;
import com.project_service.bookingservice.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto mapToDto(Client client);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    Client mapToEntity(ClientDto clientDto);
}
