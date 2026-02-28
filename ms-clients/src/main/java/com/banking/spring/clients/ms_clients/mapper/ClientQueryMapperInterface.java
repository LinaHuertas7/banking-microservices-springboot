package com.banking.spring.clients.ms_clients.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.banking.spring.clients.ms_clients.DTO.request.ClientQueryRequestDTO;
import com.banking.spring.clients.ms_clients.DTO.response.ClientQueryResponseDTO;
import com.banking.spring.clients.ms_clients.model.Client;

@Mapper(componentModel = "spring")
public interface ClientQueryMapperInterface {

    @Mapping(target = "correlationId", source = "request.correlationId")
    @Mapping(target = "exists", constant = "true")
    @Mapping(target = "active", source = "client.status")
    @Mapping(target = "clientId", source = "client.clientId")
    @Mapping(target = "clientName", source = "client.name")
    ClientQueryResponseDTO toResponse(Client client, ClientQueryRequestDTO request);

    @Mapping(target = "correlationId", source = "correlationId")
    @Mapping(target = "exists", constant = "false")
    @Mapping(target = "active", constant = "false")
    @Mapping(target = "clientId", ignore = true)
    @Mapping(target = "clientName", ignore = true)
    ClientQueryResponseDTO toNotFoundResponse(ClientQueryRequestDTO request);
}