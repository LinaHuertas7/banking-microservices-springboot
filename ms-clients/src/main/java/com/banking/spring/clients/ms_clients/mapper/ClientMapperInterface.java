package com.banking.spring.clients.ms_clients.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.banking.spring.clients.ms_clients.DTO.request.ClientRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.request.ClientUpdateDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientResponseDTO;
import com.banking.spring.clients.ms_clients.model.Client;

@Mapper(componentModel = "spring")
public interface ClientMapperInterface {

    @Mapping(target = "clientId", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponse(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "identification", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromDto(ClientUpdateDTO dto, @MappingTarget Client client);
}
