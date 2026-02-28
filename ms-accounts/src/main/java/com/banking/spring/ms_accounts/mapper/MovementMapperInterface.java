package com.banking.spring.ms_accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;
import com.banking.spring.ms_accounts.model.Movement;

@Mapper(componentModel = "spring")
public interface MovementMapperInterface {
    @Mapping(target = "accountNumber", source = "account.accountNumber")
    @Mapping(target = "accountSlug", source = "account.slug")
    MovementResponseDTO toResponse(Movement movement);
}
