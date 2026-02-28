package com.banking.spring.clients.ms_clients.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ClientQueryResponseDTO {
    private String correlationId;
    private boolean exists;
    private boolean active;
    private Long clientId;
    private String clientName;
}