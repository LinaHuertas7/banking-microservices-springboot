package com.banking.spring.ms_accounts.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.banking.spring.ms_accounts.DTO.request.AccountRequestDTO;
import com.banking.spring.ms_accounts.DTO.request.AccountUpdateDTO;
import com.banking.spring.ms_accounts.DTO.response.AccountResponseDTO;
import com.banking.spring.ms_accounts.mapper.annotation.IgnoreAuditFields;
import com.banking.spring.ms_accounts.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapperInterface {

    @IgnoreAuditFields
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "availableBalance", source = "initialBalance")
    Account toEntity(AccountRequestDTO dto);

    AccountResponseDTO toResponse(Account client);

    @IgnoreAuditFields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    @Mapping(target = "availableBalance", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    void updateEntityFromDto(AccountUpdateDTO dto, @MappingTarget Account account);

    @IgnoreAuditFields
    @Mapping(target = "accountId", ignore = true)
    @Mapping(target = "slug", ignore = true)
    @Mapping(target = "availableBalance", ignore = true)
    void replaceEntityFromDto(AccountRequestDTO dto, @MappingTarget Account account);
}
