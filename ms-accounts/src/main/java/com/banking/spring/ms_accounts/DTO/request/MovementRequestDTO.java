package com.banking.spring.ms_accounts.DTO.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovementRequestDTO {
    @NotNull(message = "El valor del movimiento es requerido")
    private BigDecimal amount;

    @NotNull(message = "La cuenta es requerida")
    private Long accountId;
}
