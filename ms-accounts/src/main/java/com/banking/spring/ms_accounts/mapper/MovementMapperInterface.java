package com.banking.spring.ms_accounts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;
import com.banking.spring.ms_accounts.model.Movement;

@Mapper(componentModel = "spring")
public interface MovementMapperInterface {
    @Mapping(target = "accountId", source = "account.accountId")
    @Mapping(target = "accountNumber", source = "account.accountNumber")
    MovementResponseDTO toResponse(Movement movement);
}
