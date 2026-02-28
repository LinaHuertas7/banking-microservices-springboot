package com.banking.spring.ms_accounts.DTO.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientQueryRequestDTO {
    private Long clientId;
    private String replyTo = "account.reply.queue";
    private String correlationId = UUID.randomUUID().toString();
}