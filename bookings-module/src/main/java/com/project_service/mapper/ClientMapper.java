package com.project_service.mapper;

import com.project_service.dto.ClientDto;
import com.project_service.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto mapToDto(Client client);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "isDeleted" , expression = "java(false)")
    Client mapToEntity(ClientDto clientDto);
}
