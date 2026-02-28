package com.banking.spring.ms_accounts.DTO.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreatedEventDTO {
    private Long clientId;
    private String name;
    private String identification;
}
