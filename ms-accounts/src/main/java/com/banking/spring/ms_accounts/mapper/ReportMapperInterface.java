package com.banking.spring.ms_accounts.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.banking.spring.ms_accounts.DTO.response.MovementResponseDTO;
import com.banking.spring.ms_accounts.model.Movement;

@Mapper(componentModel = "spring")
public interface ReportMapperInterface {

    @Mapping(target = "accountSlug", source = "movement.account.slug")
    @Mapping(target = "accountNumber", source = "movement.account.accountNumber")
    MovementResponseDTO toMovementResponse(Movement movement);

    List<MovementResponseDTO> toMovementResponseList(List<Movement> movements);
}