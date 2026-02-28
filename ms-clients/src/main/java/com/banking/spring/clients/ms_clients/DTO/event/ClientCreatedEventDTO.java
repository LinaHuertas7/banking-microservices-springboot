package com.banking.spring.clients.ms_clients.DTO.event;

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
