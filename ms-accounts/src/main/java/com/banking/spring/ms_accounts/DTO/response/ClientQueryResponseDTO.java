package com.banking.spring.ms_accounts.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientQueryResponseDTO {
    private String correlationId;
    private boolean exists;
    private boolean active;
    private Long clientId;
    private String clientName;
}